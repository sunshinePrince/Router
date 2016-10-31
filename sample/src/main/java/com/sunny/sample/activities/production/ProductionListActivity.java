package com.sunny.sample.activities.production;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sunny.sample.R;
import com.sunny.sample.activities.BaseActivity;
import com.sunny.sample.constants.ActionConstants;
import com.sunshineprince.router.Router;

/**
 * author : sunny
 * email : zicai346@gmail.com
 * github : https://github.com/sunshinePrince
 * blog : http://mrjoker.wang
 */
public class ProductionListActivity extends BaseActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.production_list);
		setContentView(R.layout.activity_productionlist);
		final ListView listView = (ListView) findViewById(R.id.listView);
		final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
				getResources().getStringArray(R.array.production_list));
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Router.builder(ProductionListActivity.this).target(ActionConstants.PRODUCTION_DETAIL).data("{id:\""+(position+1)+"\",name:\""+adapter.getItem(position)+"\"}").start();
			}
		});
	}


}
