package edu.wvfs.fsu.v89mobileradio;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
public class DonateFragment extends android.support.v4.app.Fragment {
	
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	   Bundle savedInstanceState) {
	  // TODO Auto-generated method stub
	  View myFragmentView = inflater.inflate(R.layout.donate_fragment, container, false);
      ImageButton payPalButton = (ImageButton) myFragmentView.findViewById(R.id.donate_button);
      payPalButton.setOnClickListener(new OnClickListener(){
    	  @Override
    		public void onClick(View v) {
    		  Uri uri = Uri.parse("https://www.paypal.com/us/cgi-bin/webscr?hosted_button_id=CWHQCHRVCVYYG&cmd=_s-xclick");
    		  Intent intent = new Intent(Intent.ACTION_VIEW, uri);
    		  startActivity(intent);
    		  
    		}
      });
	  return myFragmentView;
	 }
}
