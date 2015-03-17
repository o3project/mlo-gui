/**
 * SliceMultiRequestImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.xml.bind.JAXB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.ClientConfig;
import org.o3project.mlo.client.control.ClientConfigConstants;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.MloNbiException;
import org.o3project.mlo.client.control.SliceDataManager;
import org.o3project.mlo.client.control.SliceMultiRequest;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.SliceDto;

/**
 * This class is the implementation class for {@link SliceMultiRequest} interface.
 */
public class SliceMultiRequestImpl implements SliceMultiRequest {
	
	private static final Log LOG = LogFactory.getLog(SliceMultiRequestImpl.class);
	
	private static final Integer THREAD_POOL_SIZE = 50;
	
	private static final Integer EXEC_SERVICE_SHUTDOWN_AWAIT_TIMEOUT_SEC = 30;
	
	private static final Integer CE_PORT_NO_OFFSET = 100;
	
	private static final Integer REQ_BAND_WIDTH = 1; // 1 Mbps
	
	private static final Integer LARGE_REQ_DELAY = 9999;
	
	private static final Integer SMALL_REQ_DELAY = 10;
	
	private final Random random = new Random(System.currentTimeMillis());

	private static final int CREATE_MAX_COLUMNS = 6;
	private static final int CREATE_MIN_COLUMNS = 4;
	private static final int UPDATE_MAX_COLUMNS = 4;
	private static final int UPDATE_MIN_COLUMNS = 2;
	private static final int DELETE_COLUMNS = 2;

	private static final int TYPE_COLUMN = 1;
	private static final int SLICE_NUM_COLUMN = 2;
	private static final int FLOW_NUM_COLUMN = 3;
	private static final int CREATE_BANDWIDTH_COLUMN = 4;
	private static final int CREATE_LATENCY_COLUMN = 5;
	private static final int UPDATE_BANDWIDTH_COLUMN = 2;
	private static final int UPDATE_LATENCY_COLUMN = 3;

	private final ClientConfig clientConfig;
	
	/**
	 * A constructor.
	 * @param clientConfig the configuration instance.
	 */
	public SliceMultiRequestImpl(ClientConfig clientConfig) {
		this.clientConfig = clientConfig;
	}
	
	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceMultiRequest#doCreateRequest(int)
	 */
	@Override
	public void doCreateSlices(SliceDataManager sliceDataManager, Integer nSlice, Integer nFlowForSlice, Integer nBandWidth, Integer nLatency) throws InterruptedException {
		final ExecutorService execService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		
		List<SliceDto> reqSliceDtos = createSliceDtos(nSlice, nFlowForSlice, nBandWidth, nLatency);
		ClientSliceOperationTask task = null;
		List<Future<SliceDto>> futures = new ArrayList<>();
		for (SliceDto reqSliceDto : reqSliceDtos) {
			log("Request", reqSliceDto);
			SliceOperation operation = new SliceOperation() {
				@Override
				public SliceDto operate(SliceDataManager sdm, SliceDto slice) throws MloClientException {
					return sdm.createSliceInfo(slice);
				}
			};
			task = new ClientSliceOperationTask(reqSliceDto.name, operation, reqSliceDto, sliceDataManager);
			futures.add(execService.submit(task));
		}
		
		for (Future<SliceDto> future: futures) {
			SliceDto resSliceDto = null;
			try {
				resSliceDto = future.get();
				log("Response", resSliceDto);
			} catch (ExecutionException e) {
				handleExecutionException(e);
			}
		}
		
		execService.shutdownNow();
		execService.awaitTermination(EXEC_SERVICE_SHUTDOWN_AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS);
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceMultiRequest#doUpdateAllSlices(org.o3project.mlo.client.control.SliceDataManager)
	 */
	@Override
	public void doUpdateAllSlices(SliceDataManager sliceDataManager, Integer bandWidth, Integer latency) throws InterruptedException {
		List<SliceDto> sliceDtos = null; 
		try {
			sliceDtos = sliceDataManager.getSliceList();
		} catch (MloClientException e) {
			throw new IllegalStateException("getSliceList is failed", e);
		}
		
		if (sliceDtos == null) {
			throw new IllegalStateException("getSliceList is null");
		}
		
		final ExecutorService execService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		
		ClientSliceOperationTask task = null;
		List<Future<SliceDto>> futures = new ArrayList<>();
		for (SliceDto listSliceDto : sliceDtos) {
			SliceDto registeredSlice = null;
			try {
				registeredSlice = sliceDataManager.getSliceInfo(listSliceDto);
			} catch (MloClientException e) {
				throw new IllegalStateException("getSliceInfo is failed", e);
			}
			SliceDto updateSlice = updateSliceDto(registeredSlice, bandWidth, latency);
			log("Request", updateSlice);
			SliceOperation operation = new SliceOperation() {
				@Override
				public SliceDto operate(SliceDataManager sdm, SliceDto slice) throws MloClientException {
					return sdm.updateSliceInfo(slice);
				}
			};
			task = new ClientSliceOperationTask(listSliceDto.name, operation, updateSlice, sliceDataManager);
			futures.add(execService.submit(task));
		}
		
		for (Future<SliceDto> future: futures) {
			SliceDto resSliceDto = null;
			try {
				resSliceDto = future.get();
				log("Response", resSliceDto);
			} catch (ExecutionException e) {
				handleExecutionException(e);
			}
		}
		
		execService.shutdownNow();
		execService.awaitTermination(EXEC_SERVICE_SHUTDOWN_AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS);
		
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceMultiRequest#doDeleteAllRequest(org.o3project.mlo.client.control.SliceDataManager)
	 */
	@Override
	public void doDeleteAllSlices(SliceDataManager sliceDataManager) throws InterruptedException {
		List<SliceDto> sliceDtos = null; 
		try {
			sliceDtos = sliceDataManager.getSliceList();
		} catch (MloClientException e) {
			throw new IllegalStateException("getSliceList is failed", e);
		}
		
		if (sliceDtos == null) {
			throw new IllegalStateException("getSliceList is null");
		}
		
		final ExecutorService execService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
		
		ClientSliceOperationTask task = null;
		List<Future<SliceDto>> futures = new ArrayList<>();
		for (SliceDto reqSliceDto : sliceDtos) {
			log("Request", reqSliceDto);
			SliceOperation operation = new SliceOperation() {
				@Override
				public SliceDto operate(SliceDataManager sdm, SliceDto slice) throws MloClientException {
					return sdm.deleteSliceInfo(slice);
				}
			};
			task = new ClientSliceOperationTask(reqSliceDto.name, operation, reqSliceDto, sliceDataManager);
			futures.add(execService.submit(task));
		}
		
		for (Future<SliceDto> future: futures) {
			SliceDto resSliceDto = null;
			try {
				resSliceDto = future.get();
				log("Response", resSliceDto);
			} catch (ExecutionException e) {
				handleExecutionException(e);
			}
		}
		
		execService.shutdownNow();
		execService.awaitTermination(EXEC_SERVICE_SHUTDOWN_AWAIT_TIMEOUT_SEC, TimeUnit.SECONDS);
		
	}

	/**
	 * Creates slice DTO list for CREATE request.
	 * @param nSlice the number of slices.
	 * @param nFlowForSlice the number of flows per a slice.
	 * @param nBandWidth band width.
	 * @param nLatency delay time.
	 * @return the slice DTO list for request.
	 */
	List<SliceDto> createSliceDtos(final Integer nSlice, final Integer nFlowForSlice, final Integer nBandWidth, final Integer nLatency) {
		List<SliceDto> sliceDtos = new ArrayList<SliceDto>();
		int nTotalFlowIdx = 0;
		int portNo = CE_PORT_NO_OFFSET;
		for (int sliceIdx = 0; sliceIdx < nSlice; sliceIdx += 1) {
			SliceDto sliceDto = new SliceDto();
			sliceDto.name = String.format("slice%08d", sliceIdx);
			sliceDto.flows = new ArrayList<FlowDto>();
			for (int flowIdx = 0; flowIdx < nFlowForSlice; flowIdx += 1) {
				String flowName = String.format("flow%08d", nTotalFlowIdx);
				sliceDto.flows.add(createRequestFlowDto(flowName, portNo, nBandWidth, nLatency));
				nTotalFlowIdx += 1;
				portNo += 1;
			}
			sliceDtos.add(sliceDto);
		}
		return sliceDtos;
	}
	
	/**
	 * Creates slice DTO list for UPDATE request.
	 * @param registeredSlice the slice DTO.
	 * @param nBandWidth band width.
	 * @param nLatency delay time.
	 * @return the slice DTO list for request.
	 */
	SliceDto updateSliceDto(final SliceDto registeredSlice, final Integer nBandWidth, final Integer nLatency) {
		int nTotalFlowIdx = 0;
		int portNo = CE_PORT_NO_OFFSET;
		SliceDto sliceDto = new SliceDto();
		sliceDto.id = registeredSlice.id;
		sliceDto.flows = new ArrayList<FlowDto>();
		for (FlowDto flow : registeredSlice.flows) {
			String flowName = String.format("flow%08d", nTotalFlowIdx);
			FlowDto updateFlow = createRequestFlowDto(flowName, portNo, nBandWidth, nLatency);
			updateFlow.type = "mod";
			updateFlow.id = flow.id;
			sliceDto.flows.add(updateFlow);
			nTotalFlowIdx += 1;
			portNo += 1;
		}
		return sliceDto;
	}
	
	/**
	 * Creates flow DTO for request.
	 * @param name the flow name.
	 * @param portNo the CE port.
	 * @param bandWidth the band width.
	 * @param latency the delay time.
	 * @return the flow DTO.
	 */
	FlowDto createRequestFlowDto(String name, Integer portNo, Integer bandWidth, Integer latency) {
		String nodeNameSuffix = "";
		if(!ClientConfigConstants.CLIENT_TYPE_OTHER.equals(clientConfig.getSrcComponentName())){
			nodeNameSuffix = "123";
		}
		String sPortNo = String.format("%08d", portNo);
		FlowDto reqFlowDto = new FlowDto();
		reqFlowDto.name = name;
		reqFlowDto.srcCENodeName = "tokyo" + nodeNameSuffix;
		reqFlowDto.srcCEPortNo = sPortNo;
		reqFlowDto.dstCENodeName = "osaka" + nodeNameSuffix;
		reqFlowDto.dstCEPortNo = sPortNo;
		if (bandWidth != null) {
			reqFlowDto.reqBandWidth = bandWidth;
		} else {
			reqFlowDto.reqBandWidth = REQ_BAND_WIDTH;
		}
		if (latency != null) {
			reqFlowDto.reqDelay = latency;
		} else {
			reqFlowDto.reqDelay = random.nextBoolean() ? LARGE_REQ_DELAY : SMALL_REQ_DELAY;
		}
		reqFlowDto.protectionLevel = "0";
		return reqFlowDto;
	}

	/**
	 * Logs slice DTO.
	 * @param identifier the identifier (Request/Response)
	 * @param sliceDto the slice DTO
	 */
	static void log(String identifier, SliceDto sliceDto) {
		ByteArrayOutputStream ostream = null;
		ostream = new ByteArrayOutputStream();
		JAXB.marshal(sliceDto, ostream);
		LOG.info(String.format("==== %s. slice: %s", identifier, sliceDto.name));
		try {
			LOG.info(ostream.toString("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			LOG.error("UTF-8 unsupported", e); // Only if UTF-8 is not supported.
		}
		// ostream.close(); // Don't need close ByteArrayOutputSteam.
		ostream = null;
	}

	private void handleExecutionException(ExecutionException e) {
		LOG.warn("Error occurs.", e.getCause());
		if (e.getCause() instanceof MloNbiException) {
			MloNbiException nbie = (MloNbiException) e.getCause();
			LOG.warn("An MloNbiException occurs.");
			LOG.warn("cause : " + nbie.getErrorDto().cause);
			LOG.warn("detail: " + nbie.getErrorDto().detail);
		}
	}

	/**
	 * Main method.
	 * @param args the arguments.
	 */
	public static void main(String[] args) {
		SliceDataManager sliceDataManager = null;
		ClientConfig clientConfig = null;
		// Launches Seasar2
		try {
			SingletonS2ContainerFactory.setConfigPath("app.dicon");
			SingletonS2ContainerFactory.init();
			LOG.info("S2Container has been initialized.");
			
			sliceDataManager = SingletonS2Container.getComponent("sliceDataManager");
			clientConfig = SingletonS2Container.getComponent("clientConfig");
			LOG.info("S2 task has been started.");
		} catch (RuntimeException e) {
			LOG.fatal("Failed to initialize S2.", e);
			throw e;
		}

		SliceMultiRequest req = new SliceMultiRequestImpl(clientConfig);
		LOG.info("Start processing");
		long startTime = System.currentTimeMillis();
		doProcess(req, sliceDataManager, args);
		long endTime = System.currentTimeMillis();
		LOG.info("End processing");
		LOG.info(String.format("Processing time: %d [msec]", (endTime - startTime)));

		try {
			SingletonS2ContainerFactory.destroy();
		} catch (RuntimeException e) {
			LOG.fatal("Failed to destroy S2.", e);
			throw e;
		}
	}

	/**
	 * Executes a task.
	 * @param req the request.
	 * @param sliceDataManager the slice data manager.
	 * @param args the arguments.
	 */
	static void doProcess(SliceMultiRequest req,
			SliceDataManager sliceDataManager, String[] args) {
		try {
			if (args.length > 0 && "-m".equals(args[0])) {
				if ((args.length == CREATE_MIN_COLUMNS || args.length == CREATE_MAX_COLUMNS) && "create".equals(args[TYPE_COLUMN])) {
					int nSlice = Integer.valueOf(args[SLICE_NUM_COLUMN]);
					int nFlowForSlice = Integer.valueOf(args[FLOW_NUM_COLUMN]);
					Integer nBandWidth = null;
					Integer nLatency = null;
					if (args.length == CREATE_MAX_COLUMNS) {
						nBandWidth = Integer.valueOf(args[CREATE_BANDWIDTH_COLUMN]);
						nLatency = Integer.valueOf(args[CREATE_LATENCY_COLUMN]);
					}
					req.doCreateSlices(sliceDataManager, nSlice, nFlowForSlice, nBandWidth, nLatency);
				} else if ((args.length == UPDATE_MIN_COLUMNS || args.length == UPDATE_MAX_COLUMNS) && "update".equals(args[TYPE_COLUMN])) {
					Integer nBandWidth = null;
					Integer nLatency = null;
					if (args.length == UPDATE_MAX_COLUMNS) {
						nBandWidth = Integer.valueOf(args[UPDATE_BANDWIDTH_COLUMN]);
						nLatency = Integer.valueOf(args[UPDATE_LATENCY_COLUMN]);
					}
					req.doUpdateAllSlices(sliceDataManager, nBandWidth, nLatency);
				} else if (args.length == DELETE_COLUMNS && "delete".equals(args[TYPE_COLUMN])) {
					req.doDeleteAllSlices(sliceDataManager);
				} else {
					System.out.println("create multiple slices : -m create <SliceNum> <FlowNum> [<BandWidth> <Latency>]");
					System.out.println("update all slices      : -m update [<BandWidth> <Latency>]");
					System.out.println("delete all slices      : -m delete");
				}
			}
		} catch (InterruptedException e) {
			LOG.error("doProcess interrupted", e);
		}
	}
}

/**
 * This interface denotes slice operation.
 */
interface SliceOperation {
	/**
	 * Executes to operate slice.
	 * @param sliceDataManager the slice data manager.
	 * @param reqSliceDto the requested slice DTO.
	 * @return the received slice DTO.
	 * @throws MloClientException Failed to operate.
	 */
	SliceDto operate(SliceDataManager sliceDataManager, SliceDto reqSliceDto) throws MloClientException;
}

/**
 * This class is the task class to operate slice.
 */
class ClientSliceOperationTask implements Callable<SliceDto> {
	
	private static final Log LOG = LogFactory.getLog(ClientSliceOperationTask.class);

	private final String name;
	
	private final SliceOperation operation;
	
	private final SliceDto reqSliceDto;
	
	private final SliceDataManager sliceDataManager; 
	
	/**
	 * A constructor.
	 * @param name the name.
	 * @param operation the operation.
	 * @param reqSliceDto the requested slice DTO.
	 * @param sliceDataManager the slice data manager.
	 */
	public ClientSliceOperationTask(String name, SliceOperation operation, SliceDto reqSliceDto, SliceDataManager sliceDataManager) {
		this.name = name;
		this.operation = operation;
		this.reqSliceDto = reqSliceDto;
		this.sliceDataManager = sliceDataManager;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public SliceDto call() throws MloClientException {
		SliceDto resSliceDto = null;
		try {
			resSliceDto = operation.operate(sliceDataManager, reqSliceDto);
		} catch (MloClientException e) {
			LOG.warn("Failed to operate : " + name, e);
			throw e;
		}
		return resSliceDto;
	}
}
