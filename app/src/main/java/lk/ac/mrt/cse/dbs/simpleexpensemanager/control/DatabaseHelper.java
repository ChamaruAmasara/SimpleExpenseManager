package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_TRANSACTION = "transactionEntry";
    public static final String TABLE_ACCOUNT = "account";
    public static final String COLUMN_ACCOUNT_NO = "accountNo";
    public static final String COLUMN_BANK_NAME = "bankName";
    public static final String COLUMN_ACCOUNT_HOLDER_NAME = "accountHolderName";
    public static final String COLUMN_BALANCE = "balance";
    public static final String COLUMN_TRANSACTION_NO = "transactionNo";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_EXPENSE_TYPE = "expenseType";
    public static final String COLUMN_AMOUNT = "amount";


    public static DatabaseHelper instance;

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    };

    public DatabaseHelper(@Nullable Context context) {
        super(context, "expense.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {

            String createAccountTable = "CREATE TABLE " + TABLE_ACCOUNT + "(" + COLUMN_ACCOUNT_NO + " TEXT PRIMARY KEY, " + COLUMN_BANK_NAME + " TEXT, " + COLUMN_ACCOUNT_HOLDER_NAME + " TEXT, " + COLUMN_BALANCE + " REAL)";
            sqLiteDatabase.execSQL(createAccountTable);
            //add transactionNo as primary key
            //transactionNo INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, accountNo TEXT, expenseType TEXT, amount REAL, FOREIGN KEY(accountNo) REFERENCES account(accountNo))";
            String createTransactionTable = "CREATE TABLE " + TABLE_TRANSACTION + "(" + COLUMN_TRANSACTION_NO + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DATE + " TEXT, " + COLUMN_ACCOUNT_NO + " TEXT, " + COLUMN_EXPENSE_TYPE + " TEXT, " + COLUMN_AMOUNT +
                    " REAL, FOREIGN KEY(" + COLUMN_ACCOUNT_NO + ") REFERENCES account(" + COLUMN_ACCOUNT_NO + "))";
            sqLiteDatabase.execSQL(createTransactionTable);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addToAccountTable(Account account){
        //get variables in account
        String accountNo = account.getAccountNo();
        String bankName = account.getBankName();
        String accountHolderName = account.getAccountHolderName();
        double balance = account.getBalance();


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ACCOUNT_NO, accountNo);
        contentValues.put(COLUMN_BANK_NAME, bankName);
        contentValues.put(COLUMN_ACCOUNT_HOLDER_NAME, accountHolderName);
        contentValues.put(COLUMN_BALANCE, balance);
        long result = db.insert(TABLE_ACCOUNT, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
}
