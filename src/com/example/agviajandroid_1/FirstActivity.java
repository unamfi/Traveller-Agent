package com.example.agviajandroid_1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

public class FirstActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
	}

	public void onRadioButtonClicked(View view) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.node1:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 1);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node2:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 2);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node3:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 3);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node4:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 4);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node5:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 5);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node6:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 6);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node7:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 7);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node8:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 8);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        case R.id.node9:
	            if (checked){
	            	Intent resultIntent = new Intent();
	            	resultIntent.putExtra("nodo", 9);
	            	// TODO Add extras or a data URI to this intent as appropriate.
	            	setResult(Activity.RESULT_OK, resultIntent);
	            	finish();
	            }
	                // Pirates are the best
	            break;
	        
	    }
	}

}
