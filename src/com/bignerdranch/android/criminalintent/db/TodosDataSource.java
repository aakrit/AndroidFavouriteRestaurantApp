//package com.bignerdranch.android.criminalintent.db;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.util.Log;
//
//import com.bignerdranch.android.criminalintent.Restaurant;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import edu.uchicago.cs.gerber.Todo;
//
//public class TodosDataSource {
//
//	private static String TAG = "Todos";
//	// Database fields
//	private SQLiteDatabase mSQLiteDatabase;
//	private TodosSQLiteHelper mTodosSQLiteHelper;
//	private String[] mColumns = { TodosSQLiteHelper.COLUMN_ID,
//			TodosSQLiteHelper.COLUMN_COMMENT, TodosSQLiteHelper.COLUMN_DETAIL };
//
//	public TodosDataSource(Context context) {
//		mTodosSQLiteHelper = new TodosSQLiteHelper(context);
//	}
//
//	public void open() throws SQLException {
//		mSQLiteDatabase = mTodosSQLiteHelper.getWritableDatabase();
//	}
//
//	public void close() {
//		mTodosSQLiteHelper.close();
//	}
//
//	public boolean isOpen(){
//		return (mSQLiteDatabase != null);
//	}
//
//	// basic CRUD operations
//	// CREATE
//	public Restaurant createTodo(String strTitle, String detail) {
//		ContentValues values = new ContentValues();
//		values.put(TodosSQLiteHelper.COLUMN_COMMENT, strTitle);
//		values.put(TodosSQLiteHelper.COLUMN_DETAIL, detail);
//		long insertId = mSQLiteDatabase.insert(TodosSQLiteHelper.TABLE_TODOS, null,
//				values);
//		Cursor cursor = mSQLiteDatabase.query(TodosSQLiteHelper.TABLE_TODOS,
//                mColumns, TodosSQLiteHelper.COLUMN_ID + " = " + insertId, null,
//				null, null, null);
//		cursor.moveToFirst();
//        Restaurant newTodo = cursorToTodo(cursor);
//		cursor.close();
//		return newTodo;
//	}
//
//	// READ
//	public List<Restaurant> getAllTodos() {
//		List<Restaurant> todos = new ArrayList<Restaurant>();
//
//		Cursor cursor = mSQLiteDatabase.query(TodosSQLiteHelper.TABLE_TODOS,
//                mColumns, null, null, null, null, null);
//
//		cursor.moveToFirst();
//		while (!cursor.isAfterLast()) {
//            Restaurant Todo = cursorToTodo(cursor);
//			todos.add(Todo);
//			cursor.moveToNext();
//		}
//		// Make sure to close the cursor
//		cursor.close();
//		return todos;
//	}
//
//	// get one Todo
//	// UPDATE
//	public int updateTodo(Todo com) {
//
//		ContentValues cv = new ContentValues();
//
//		cv.put(TodosSQLiteHelper.COLUMN_COMMENT, com.getTitle());
//		cv.put(TodosSQLiteHelper.COLUMN_DETAIL, com.getDetail());
//
//		Log.i(TAG, "Todo updated with id: " + com.getId());
//
//		return mSQLiteDatabase.update(TodosSQLiteHelper.TABLE_TODOS, cv,
//				TodosSQLiteHelper.COLUMN_ID + " =?",
//				new String[] { String.valueOf(com.getId()) });
//
//	}
//
//	// DELETE
//	public void deleteTodo(Restaurant Todo) {
//		long id = Todo.getId();
//
//		mSQLiteDatabase.delete(TodosSQLiteHelper.TABLE_TODOS,
//                TodosSQLiteHelper.COLUMN_ID + " = " + id, null);
//		Log.i(TAG, "Todo deleted with id: " + id);
//	}
//
//	//overriden to take an id
//	public void deleteTodo(long lId) {
//		mSQLiteDatabase.delete(TodosSQLiteHelper.TABLE_TODOS,
//                TodosSQLiteHelper.COLUMN_ID + " = " + lId, null);
//		Log.i(TAG, "Todo deleted with id: " + lId);
//	}
//
//
//	private Restaurant cursorToTodo(Cursor cursor) {
//        Restaurant Todo = new Todo();
//		Todo.setId(cursor.getLong(0));
//		Todo.setTitle(cursor.getString(1));
//		Todo.setDetail(cursor.getString(2));
//		return Todo;
//	}
//}