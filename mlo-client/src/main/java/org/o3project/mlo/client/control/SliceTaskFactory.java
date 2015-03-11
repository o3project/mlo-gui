/**
 * SliceTaskFactory.java
 * (C) 2014, Hitachi Solutions, Ltd.
 */
package org.o3project.mlo.client.control;

/**
 * スライス操作用のタスクを作成するファクトリインタフェースです。
 */
public interface SliceTaskFactory {
	
	/**
	 * 確認メッセージ：スライス生成
	 */
    String CREATE_TASK_LB = "Will you continue the create process ?";
    
	/**
	 * 確認メッセージ：スライス削除
	 */
    String DELETE_TASK_LB = "Will you continue the delete process ?";
    
	/**
	 * 確認メッセージ：スライス更新
	 */
    String UPDATE_TASK_LB = "Will you continue the update process ?";
    
	/**
	 * 確認メッセージ：キャンセル
	 */
    String CANCEL_TASK_LB = "Will you continue the cancel process ?";
    
	/**
	 * 確認メッセージ：スライス一覧取得
	 */
    String LIST_TASK_LB = "Is get slice list ?";
    
	/**
	 * 確認メッセージ：スライス情報取得
	 */
    String READ_TASK_LB = "Is get slice info ?";

	/**
	 * 確認メッセージ：簡易表示用スライス更新
	 */
    String SIMPLE_UPDATE_TASK_LB = "Will you continue the simple update process ?";

	/**
	 * スライス操作用のタスクを作成します。
	 * @param taskKey スライス操作キー
	 * @return タスクインスタンス
	 */
	SliceTask createTask(String taskKey);
}
