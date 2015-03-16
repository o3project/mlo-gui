/**
 * SliceTaskFactoryImpl.java
 * (C) 2014,2015, Hitachi, Ltd.
 */
package org.o3project.mlo.client.impl.control;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.o3project.mlo.client.control.MloClientException;
import org.o3project.mlo.client.control.SliceTask;
import org.o3project.mlo.client.control.SliceTaskFactory;

import org.o3project.mlo.server.dto.SliceDto;

/**
 * This class is the implementation class of {@link SliceTaskFactory} interface.
 */
public class SliceTaskFactoryImpl implements SliceTaskFactory {
    private static final Log LOG = LogFactory.getLog(SliceTaskFactoryImpl.class);

	private final MloClient mloClient;
	
	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public SliceTaskFactoryImpl(MloClient mloClient) {
		this.mloClient = mloClient;
	}

	/* (non-Javadoc)
	 * @see org.o3project.mlo.client.control.SliceTaskFactory#createTask(java.lang.String)
	 */
	@Override
	public SliceTask createTask(String taskKey) {
		SliceTask task = null;
		LOG.debug("Creating task for " + taskKey);
        if (CREATE_TASK_LB.equals(taskKey)) {
        	task = new CreateSliceTask(mloClient);
        } else if (DELETE_TASK_LB.equals(taskKey)) {
        	task = new DeleteSliceTask(mloClient);
        } else if (UPDATE_TASK_LB.equals(taskKey)) {
        	task = new UpdateSliceTask(mloClient);
        } else if (CANCEL_TASK_LB.equals(taskKey)) {
        	task = new CancelTask(mloClient);
        } else if (LIST_TASK_LB.equals(taskKey)) {
        	task = new GetSlicesTask(mloClient);
        } else if (READ_TASK_LB.equals(taskKey)) {
        	task = new ReadSliceTask(mloClient);
        } else {
            // Otherwise,
        	LOG.error("Invalid key specified: " + taskKey);
            throw new UnsupportedOperationException("Invalid key specified: " + taskKey);
        }

		return task;
	}
}

/**
 * This class designates the task canceling.
 * Execution this task always calls "canceled".
 */
class CancelTask extends SliceTask {

	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public CancelTask(MloClient mloClient) {
		super(mloClient);
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() {
		cancel();
		return null;
	}
}

/**
 * This class designates the slice creation task.
 */
class CreateSliceTask extends SliceTask {

	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public CreateSliceTask(MloClient mloClient) {
		super(mloClient);
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() {
        try {
            SliceDto sliceDto = getMloClient().createViewCtrl.getData();
            SliceDto newSliceDto = getMloClient().getSliceDataManager().createSliceInfo(sliceDto);
            setSliceDtoList(Arrays.asList(newSliceDto));
        } catch (MloClientException e) {
            LOG.warn("Failed to create a slice.", e);
            setError(e);
        }
		return null;
	}
}

/**
 * This class designates the slice deletion task.
 */
class DeleteSliceTask extends SliceTask {

	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public DeleteSliceTask(MloClient mloClient) {
		super(mloClient);
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() {
        try {
            SliceDto sliceDto = getMloClient().infoViewCtrl.getDeleteSliceData();
            SliceDto newSliceDto = getMloClient().getSliceDataManager().deleteSliceInfo(sliceDto);
            setSliceDtoList(Arrays.asList(newSliceDto));
        } catch (MloClientException e) {
            LOG.warn("Failed to delete a slice.", e);
            setError(e);
        }
        return null;
	}
}

/**
 * This class designates the slice updating task.
 */
class UpdateSliceTask extends SliceTask {

	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public UpdateSliceTask(MloClient mloClient) {
		super(mloClient);
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() {
        try {
            SliceDto sliceDto = getMloClient().updateViewCtrl.getData();
            SliceDto newSliceDto = getMloClient().getSliceDataManager().updateSliceInfo(sliceDto);
            setSliceDtoList(Arrays.asList(newSliceDto));
        } catch (MloClientException e) {
            LOG.warn("Failed to update a slice.", e);
            setError(e);
        }
        return null;
	}
}

/**
 * This class designates the slice list retrieving task.
 */
class GetSlicesTask extends SliceTask {

	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public GetSlicesTask(MloClient mloClient) {
		super(mloClient);
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() throws Exception {
        try {
            List<SliceDto> sliceDtos = getMloClient().getSliceDataManager().getSliceList();
            setSliceDtoList(sliceDtos);
        } catch (MloClientException e) {
            LOG.warn("Failed to get slice list.", e);
            setError(e);
        }
        return null;
	}
}

/**
 * This class designates the slice retrieving task.
 */
class ReadSliceTask extends SliceTask {

	/**
	 * A constructor.
	 * @param mloClient the {@link MloClient} instance.
	 */
	public ReadSliceTask(MloClient mloClient) {
		super(mloClient);
	}

	/* (non-Javadoc)
	 * @see javafx.concurrent.Task#call()
	 */
	@Override
	protected Void call() throws Exception {
        try {
            SliceDto sliceDto = getMloClient().infoViewCtrl.getReadSliceDto();
            SliceDto newSliceDto = getMloClient().getSliceDataManager().getSliceInfo(sliceDto);
            setSliceDtoList(Arrays.asList(newSliceDto));
        } catch (MloClientException e) {
            LOG.warn("Failed to read a slice.", e);
            setError(e);
        }
		return null;
	}
}
