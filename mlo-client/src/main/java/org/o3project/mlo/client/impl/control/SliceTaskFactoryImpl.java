/**
 * SliceTaskFactoryImpl.java
 * (C) 2014, Hitachi Solutions, Ltd.
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
 * {@link SliceTaskFactory} インタフェースの実装クラスです。
 */
public class SliceTaskFactoryImpl implements SliceTaskFactory {
    private static final Log LOG = LogFactory.getLog(SliceTaskFactoryImpl.class);

	private final MloClient mloClient;
	
	/**
	 * 指定された情報にてインスタンスを生成します。
	 * @param mloClient {@link MloClient} インスタンス
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
            // 上記以外の処理
        	LOG.error("Invalid key specified: " + taskKey);
            throw new UnsupportedOperationException("Invalid key specified: " + taskKey);
        }

		return task;
	}
}

/**
 * キャンセル処理を表現するタスククラスです。
 * このタスクを実行すると canceled が必ず呼び出されます。
 */
class CancelTask extends SliceTask {

	/**
	 * コンストラクタ
	 * @param mloClient {@link MloClient} インスタンス
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
 * スライス作成処理タスククラスです。
 */
class CreateSliceTask extends SliceTask {

	/**
	 * コンストラクタ
	 * @param mloClient {@link MloClient} インスタンス
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
 * スライス削除処理タスククラスです。
 */
class DeleteSliceTask extends SliceTask {

	/**
	 * コンストラクタ
	 * @param mloClient {@link MloClient} インスタンス
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
 * スライス更新処理タスククラスです。
 */
class UpdateSliceTask extends SliceTask {

	/**
	 * コンストラクタ
	 * @param mloClient {@link MloClient} インスタンス
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
 * スライス一覧取得処理タスククラスです。
 */
class GetSlicesTask extends SliceTask {

	/**
	 * 指定された情報にてインスタンスを生成します。
	 * @param mloClient {@link MloClient} インスタンス
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
 * スライス読み込み処理タスククラスです。
 */
class ReadSliceTask extends SliceTask {

	/**
	 * コンストラクタ
	 * @param mloClient {@link MloClient} インスタンス
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
