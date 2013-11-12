package com.andbase.demo.activity;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbTitleBar;
import com.andbase.R;
import com.andbase.demo.dao.UserInsideDao;
import com.andbase.demo.model.LocalUser;
import com.andbase.demo.model.Stock;
import com.andbase.global.MyApplication;
/**
 * ���ƣ�DBOne2OneActivity
 * ������һ��һ����
 * @author zhaoqp
 * @date 2011-12-13
 * @version
 */
public class DBOne2OneActivity extends AbActivity {
	
	private MyApplication application;
	//�������ݿ����ʵ����
	private UserInsideDao userDao = null;
	private TextView sourseData  = null;
	private TextView resultData = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setAbContentView(R.layout.db_show);
        
        AbTitleBar mAbTitleBar = this.getTitleBar();
        mAbTitleBar.setTitleText(R.string.db_one2one_name);
        mAbTitleBar.setLogo(R.drawable.button_selector_back);
        mAbTitleBar.setTitleLayoutBackground(R.drawable.top_bg);
        mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
        mAbTitleBar.setLogoLine(R.drawable.line);
	    
        application = (MyApplication)abApplication;
        Button insertBtn  = (Button)this.findViewById(R.id.insertBtn);
        Button queryBtn  = (Button)this.findViewById(R.id.queryBtn);
        Button deleteBtn  = (Button)this.findViewById(R.id.deleteBtn);
        sourseData  = (TextView)this.findViewById(R.id.sourseData);
        resultData  = (TextView)this.findViewById(R.id.resultData);
        
        //��ʼ�����ݿ����ʵ����
	    userDao = new UserInsideDao(DBOne2OneActivity.this);
	    
	    //��������
		final LocalUser mLocalUser  = new LocalUser();
		mLocalUser.setuId("99");
		mLocalUser.setName("��������");
		
		Stock mStock = new Stock();
		mStock.setuId("99");
		mStock.setText1("��������");
		
		mLocalUser.setStock(mStock);
		
	    
        insertBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//����
				
				//��ʾ���������
				sourseData.setText("�������ݣ�");
				sourseData.append("\nlocal_user{");
				sourseData.append("\nuId:"+mLocalUser.get_id());
				sourseData.append(",name:"+mLocalUser.getName());
				sourseData.append(",\nstock:{");
				sourseData.append("uId:"+mLocalUser.getStock().getuId());
				sourseData.append(",text1:"+mLocalUser.getStock().getText1());
				sourseData.append("}\n}");
				
				
				//(1)��ȡ���ݿ�
				userDao.startWritableDatabase(false);
				//(2)ִ�в�ѯ
				long id = userDao.insert(mLocalUser);
				//(3)�ر����ݿ�
				userDao.closeDatabase(false);
				
			}
		});
        
        queryBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				queryData();
			}
        });
        
        deleteBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//����
				//(1)��ȡ���ݿ�
				userDao.startWritableDatabase(false);
				//(2)ִ�в�ѯ
				userDao.deleteAll();
				//(3)�ر����ݿ�
				userDao.closeDatabase(false);
			}
		});
       
       
      } 
    
     public void queryData(){
    	    //��ѯ���������Ƿ�ɹ���
			userDao.startReadableDatabase(false);
			List<LocalUser>  mLocalUserList = userDao.queryList();
			userDao.closeDatabase(false);
			resultData.setText("��ѯ���Ϊ��");
			if(mLocalUserList==null || mLocalUserList.size()==0){
				resultData.append("��ѯ���Ϊ��0");
			}else{
				for(LocalUser localUser:mLocalUserList){
					Stock stock = localUser.getStock();
					resultData.append("\nlocal_user{\n_id:"+localUser.get_id()+",uId:"+localUser.getuId()+",name:"+localUser.getName());
					if(stock!=null){
						resultData.append("\n,stock{_id:"+stock.get_id()+",uId:"+stock.getuId()+",text1:"+stock.getText1()+"}");
					}
					
					
					resultData.append("\n}");
					
					resultData.append("\n-----------------------");
				}
			}
     }
    
}