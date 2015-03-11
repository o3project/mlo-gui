/**
 * RequestorServiceImpl.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.bind.JAXB;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.ClientConfig;
import org.o3project.mlo.client.control.ClientConfigConstants;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.RequestorService;
import org.o3project.mlo.client.control.SliceDataManager;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import org.o3project.mlo.server.dto.FlowDto;
import org.o3project.mlo.server.dto.RestifRequestDto;
import org.o3project.mlo.server.dto.SliceDto;

/**
 * {@link RequestorService} インタフェースの実装クラスです。
 */
public class RequestorServiceImpl implements RequestorService {
	
	private static final Log LOG = LogFactory.getLog(RequestorServiceImpl.class);

	private static final int BANDWIDTH_5000 = 5000;
	private static final int BANDWIDTH_100 = 100;
	private static final int DELAY_9999 = 9999;
	private static final int DELAY_9998 = 9998;
	private static final int DELAY_10 = 10;
	private static final int DELAY_2 = 2;

	private static final int REQUEST_COLUMNS = 4;
	private static final int SERIAL_COLUMNS = 2;

	private static final int TYPE_COLUMN = 1;
	private static final int FILE_COLUMN = 2;
	private static final int THREAD_COLUMN = 3;

	private SliceDataManager sliceDataManager;
	
	private String requestType;

	private int flowCount = 1;
	
	/**
	 * 並列実行用タスク
	 */
	private class RequestTask implements Callable<SliceDto> {

		private SliceDto request;
		public RequestTask(SliceDto request) {
			this.request = request;
		}
		
	    @Override
	    public SliceDto call() throws Exception {

	    	SliceDto resSlice = null;
	    	try {
		    	switch (requestType) {
		    	case "create":
		    		resSlice = sliceDataManager.createSliceInfo(request);
		    		break;
		    	case "update":
		    		resSlice = sliceDataManager.updateSliceInfo(request);
		    		break;
		    	case "delete":
		    		resSlice = sliceDataManager.deleteSliceInfo(request);
		    		break;
		    	case "read":
		    		resSlice = sliceDataManager.getSliceInfo(request);
		    		break;
		    	default:
		    		throw new UnsupportedOperationException("request type is undefined");
		    	}
	    	} catch (Exception e) {
	    		System.out.println("##### Exception slice : " + request.name);
	    		throw e;
	    	}
	    	return resSlice;
	    }
	}
	
	/**
	 * 指定された情報にてインスタンスを生成します。
	 * @param sliceDataManager スライス情報管理
	 */
	public RequestorServiceImpl(SliceDataManager sliceDataManager) {
		this.sliceDataManager = sliceDataManager;
	}
	
	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.RequestorService#request(java.lang.String, org.o3project.mlo.server.dto.RestifRequestDto, int)
	 */
	@Override
	public void request(String type, RestifRequestDto requestTemplate, int threadNum) {

		this.requestType = type;
		
		ExecutorService service = Executors.newFixedThreadPool(threadNum);
		ArrayList<Future<SliceDto>> futures = new ArrayList<Future<SliceDto>>();
	    
	    for (int i = 1; i <= threadNum; i++) {
			SliceDto request = creareRequestDto(type, i, requestTemplate.slice);
		    futures.add(service.submit(new RequestTask(request)));
	    }
	    for (Future<SliceDto> future : futures) {
	    	try {
	    		SliceDto res = future.get();
	    		System.out.println("##### Complete task : " + (res == null ? "null" : res.name));
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
	    }
	    System.out.println("task end");

	    service.shutdown();
	    
		return;
		
	}
	
	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.RequestorService#requestMloClient()
	 */
	@Override
	public void requestMloClient() {
		try {
			SliceDto resSlice1 = sliceDataManager.createSliceInfo(createMloClientDto());
			displayResponse("CREATE", resSlice1);
			SliceDto resSlice2 = sliceDataManager.getSliceInfo(readDto(resSlice1));
			displayResponse("READ", resSlice2);
			SliceDto resSlice3 = sliceDataManager.updateSliceInfo(updateAddMloClientDto(resSlice2));
			displayResponse("UPDATE", resSlice3);
			SliceDto resSlice4 = sliceDataManager.getSliceInfo(readDto(resSlice3));
			displayResponse("READ", resSlice4);
			SliceDto resSlice5 = sliceDataManager.updateSliceInfo(updateModMloClientDto(resSlice4));
			displayResponse("UPDATE", resSlice5);
			SliceDto resSlice6 = sliceDataManager.getSliceInfo(readDto(resSlice5));
			displayResponse("READ", resSlice6);
			SliceDto resSlice7 = sliceDataManager.updateSliceInfo(updateDelDto(resSlice6));
			displayResponse("UPDATE", resSlice7);
			SliceDto resSlice8 = sliceDataManager.getSliceInfo(readDto(resSlice7));
			displayResponse("READ", resSlice8);
			SliceDto resSlice9 = sliceDataManager.deleteSliceInfo(deleteDto(resSlice8));
			displayResponse("DELETE", resSlice9);
		} catch (MloClientException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.RequestorService#requestDemoApl()
	 */
	@Override
	public void requestDemoApl() {
		try {
			SliceDto resSlice1 = sliceDataManager.createSliceInfo(createDemoAplDto());
			displayResponse("CREATE", resSlice1);
			SliceDto resSlice2 = sliceDataManager.getSliceInfo(readDto(resSlice1));
			displayResponse("READ", resSlice2);
			SliceDto resSlice3 = sliceDataManager.updateSliceInfo(updateAddDemoAplDto(resSlice2));
			displayResponse("UPDATE", resSlice3);
			SliceDto resSlice4 = sliceDataManager.getSliceInfo(readDto(resSlice3));
			displayResponse("READ", resSlice4);
			SliceDto resSlice5 = sliceDataManager.updateSliceInfo(updateModDemoAplDto(resSlice4));
			displayResponse("UPDATE", resSlice5);
			SliceDto resSlice6 = sliceDataManager.getSliceInfo(readDto(resSlice5));
			displayResponse("READ", resSlice6);
			SliceDto resSlice7 = sliceDataManager.updateSliceInfo(updateDelDto(resSlice6));
			displayResponse("UPDATE", resSlice7);
			SliceDto resSlice8 = sliceDataManager.getSliceInfo(readDto(resSlice7));
			displayResponse("READ", resSlice8);
			SliceDto resSlice9 = sliceDataManager.deleteSliceInfo(deleteDto(resSlice8));
			displayResponse("DELETE", resSlice9);
		} catch (MloClientException e) {
			e.printStackTrace();
		}
	}
	
	private SliceDto creareRequestDto(String type, int seq, SliceDto slice) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.name = String.format("%s%04d", slice.name, seq);
		if (!type.equals("create")) {
			reqSlice.id = slice.id;
		}
		
		if (null != slice.flows) {
			List<FlowDto> flows = new ArrayList<FlowDto>();
			for (FlowDto flow : slice.flows) {
				FlowDto reqFlow = new FlowDto();
				reqFlow.type = flow.type;
				reqFlow.name = String.format("flow%04d", flowCount);
				flowCount++;
				reqFlow.id = flow.id;
				reqFlow.srcCENodeName = flow.srcCENodeName;
				reqFlow.srcCEPortNo = flow.srcCEPortNo;
				reqFlow.dstCENodeName = flow.dstCENodeName;
				reqFlow.dstCEPortNo = flow.dstCEPortNo;
				reqFlow.reqBandWidth = flow.reqBandWidth;
				reqFlow.reqDelay = flow.reqDelay;
				reqFlow.protectionLevel = flow.protectionLevel;
				flows.add(reqFlow);
			}
			reqSlice.flows = flows;
		}
		
		return reqSlice;
	}
	
	private void displayResponse(String operation, SliceDto dto) {
		
		List<Integer> flowIdList = new ArrayList<Integer>();
		if (null != dto.flows) {
			for (FlowDto flow : dto.flows) {
				flowIdList.add(flow.id);
			}
		}
		System.out.println("<" + operation + "> Slice : " + dto.id + ", Flows : " + flowIdList);
	}
	
	/**
	 * 日立単独デモ環境を想定した、追加用スライス情報を作成する。
	 * @return 追加用SliceDto
	 */
	private SliceDto createMloClientDto() {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.name = String.format("slice1");
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		reqFlow.name = "flow1";
		reqFlow.srcCENodeName = "tokyo123";
		reqFlow.srcCEPortNo = "00000100";
		reqFlow.dstCENodeName = "osaka123";
		reqFlow.dstCEPortNo = "00000100";
		reqFlow.reqBandWidth = BANDWIDTH_100;
		reqFlow.reqDelay = DELAY_10;
		reqFlow.protectionLevel = "0";
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}
	
	/**
	 * 他社連携デモ環境を想定した、追加用スライス情報を作成する。
	 * @return 追加用SliceDto
	 */
	private SliceDto createDemoAplDto() {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.name = String.format("slice1");
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		
		reqFlow.name = "flow1001";
		reqFlow.srcCENodeName = "tokyo";
		reqFlow.srcCEPortNo = "00000001";
		reqFlow.dstCENodeName = "osaka";
		reqFlow.dstCEPortNo = "00000004";
		reqFlow.reqBandWidth = BANDWIDTH_5000;
		reqFlow.reqDelay = DELAY_9999;
		reqFlow.protectionLevel = "0";
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}

	/**
	 * 情報取得リクエスト用のSliceDtoを作成する。
	 * @param dto スライス情報
	 * @return 情報取得用SliceDto
	 */
	private SliceDto readDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		return reqSlice;
	}
	
	/**
	 * 日立単独デモ環境を想定した、更新用スライス情報（フロー追加）を作成する。
	 * @param dto スライス情報（readで取得した内容）
	 * @return 更新用SliceDto
	 */
	private SliceDto updateAddMloClientDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		reqFlow.type = "add";
		reqFlow.name = "flow2";
		reqFlow.srcCENodeName = "osaka123";
		reqFlow.srcCEPortNo = "00000100";
		reqFlow.dstCENodeName = "tokyo123";
		reqFlow.dstCEPortNo = "00000100";
		reqFlow.reqBandWidth = BANDWIDTH_5000;
		reqFlow.reqDelay = DELAY_2;
		reqFlow.protectionLevel = "0";
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}
	
	/**
	 * 他社連携デモ環境を想定した、更新用スライス情報（フロー追加）を作成する。
	 * @param dto スライス情報（readで取得した内容）
	 * @return 更新用SliceDto
	 */
	private SliceDto updateAddDemoAplDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		
		reqFlow.type = "add";
		reqFlow.name = "flow1002";
		reqFlow.srcCENodeName = "akashi";
		reqFlow.srcCEPortNo = "00000005";
		reqFlow.dstCENodeName = "tokyo";
		reqFlow.dstCEPortNo = "00000002";
		reqFlow.reqBandWidth = BANDWIDTH_5000;
		reqFlow.reqDelay = DELAY_9999;
		reqFlow.protectionLevel = "0";		
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}
	
	/**
	 * 日立単独デモ環境を想定した、更新用スライス情報（フロー変更）を作成する。
	 * @param dto スライス情報（readで取得した内容）
	 * @return 更新用SliceDto
	 */
	private SliceDto updateModMloClientDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		reqFlow.type = "mod";
		reqFlow.name = "flow2";
		reqFlow.id = dto.flows.get(1).id;
		reqFlow.srcCENodeName = "osaka123";
		reqFlow.srcCEPortNo = "00000100";
		reqFlow.dstCENodeName = "tokyo123";
		reqFlow.dstCEPortNo = "00000100";
		reqFlow.reqBandWidth = BANDWIDTH_100;
		reqFlow.reqDelay = DELAY_10;
		reqFlow.protectionLevel = "0";
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}

	/**
	 * 他社連携デモ環境を想定した、更新用スライス情報（フロー変更）を作成する。
	 * @param dto スライス情報（readで取得した内容）
	 * @return 更新用SliceDto
	 */
	private SliceDto updateModDemoAplDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		
		reqFlow.type = "mod";
		reqFlow.name = "flow1002";
		reqFlow.id = dto.flows.get(1).id;
		reqFlow.srcCENodeName = "akashi";
		reqFlow.srcCEPortNo = "00000005";
		reqFlow.dstCENodeName = "tokyo";
		reqFlow.dstCEPortNo = "00000002";
		reqFlow.reqBandWidth = BANDWIDTH_5000;
		reqFlow.reqDelay = DELAY_9998;
		reqFlow.protectionLevel = "0";
		
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}

	/**
	 * 更新リクエスト用（フロー削除）のSliceDtoを作成する
	 * @param dto スライス情報（readで取得した内容）
	 * @return 更新用SliceDto
	 */
	private SliceDto updateDelDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		List<FlowDto> flows = new ArrayList<FlowDto>();
		FlowDto reqFlow = new FlowDto();
		reqFlow.type = "del";
		reqFlow.id = dto.flows.get(1).id;
		flows.add(reqFlow);
		reqSlice.flows = flows;
		
		return reqSlice;
	}

	/**
	 * 削除リクエスト用のSliceDtoを作成する
	 * @param dto スライス情報（readで取得した内容）
	 * @return 削除用SliceDto
	 */
	private SliceDto deleteDto(SliceDto dto) {
		
		SliceDto reqSlice = new SliceDto();
		
		reqSlice.id = dto.id;
		
		return reqSlice;
	}
	
	/**
	 * スライス操作 CLI のメイン関数です。
	 * @param args 引数
	 */
	public static void main(String[] args) {
		SliceDataManager sliceDataManager = null;
		ClientConfig clientConfig = null;
		//　Seasar2 起動
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
		
		RequestorService requestor = new RequestorServiceImpl(sliceDataManager);

		LOG.info("Start processing");
		long startTime = System.currentTimeMillis();
		doProcess(requestor, clientConfig, args);
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
	 * 処理を実行します。
	 * @param requestor 実行インスタンス
	 * @param clientConfig コンフィグ管理
	 * @param args 引数
	 */
	static void doProcess(RequestorService requestor, ClientConfig clientConfig, String[] args) {
		String requestType = null;
		String fileName = null;
		int threadNum = 0;
		
		if (args.length > 0 && "-t".equals(args[0])) {
			if (args.length == SERIAL_COLUMNS && "serial".equals(args[1])) {
				requestType = args[TYPE_COLUMN];
			} else if (args.length == REQUEST_COLUMNS) {
				requestType = args[TYPE_COLUMN];
				fileName = args[FILE_COLUMN];
				threadNum = Integer.valueOf(args[THREAD_COLUMN]);
			} else {
				System.out.println("Parallel multiple slice request : -t <create/update/delete/read> <request FileName> <ThreadNum>");
				System.out.println("Continuous operation            : -t serial");
				return;
			}
			
			if ("serial".equals(requestType)) {
				if (ClientConfigConstants.CLIENT_TYPE_HITACHI.equals(clientConfig.getSrcComponentName())) {
					System.out.println("mloClient sequence START");
					while (!Thread.currentThread().isInterrupted()) {
						requestor.requestMloClient();
					}
				} else if (ClientConfigConstants.CLIENT_TYPE_OTHER.equals(clientConfig.getSrcComponentName())) {
					System.out.println("demoApl sequence START");
					while (!Thread.currentThread().isInterrupted()) {
						requestor.requestDemoApl();
					}
				}
			} else {
				InputStream is = null;
				try {
					try {
						is = new FileInputStream(fileName);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						return;
					}
					RestifRequestDto requestTemplate = JAXB.unmarshal(is, RestifRequestDto.class);
					requestor.request(requestType, requestTemplate, threadNum);
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
