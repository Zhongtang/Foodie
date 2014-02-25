package com.yahoo.foodie.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.yahoo.foodie.R;
import com.yahoo.foodie.clients.FoodieUserAdapter;
import com.yahoo.foodie.clients.ParseClient;
import com.yahoo.foodie.models.FoodieUser;
import com.yahoo.foodie.models.Friends;

public class InvitationFragment extends DialogFragment implements OnClickListener {
	private FoodieUserAdapter adapter;
	private ListView lvFriends;
	private Button btnInvite;
	private Button btnCancel;
	private EditText etMsg;

	private DatePicker datePicker;
	private TimePicker timePicker;
	private Calendar cal;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
    public InvitationFragment() {
        // Empty constructor required for DialogFragment
    }

    public static InvitationFragment newInstance(String title) {
    	InvitationFragment frag = new InvitationFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_invitation, container, false);
		setupView(v);
		loadMyFriends();
		return v;
	}
	
	private void setupView(View v) {
		lvFriends = (ListView) v.findViewById(R.id.lvFriends);
		etMsg = (EditText) v.findViewById(R.id.etMsg);
		btnInvite = (Button) v.findViewById(R.id.btnInvite);
		btnInvite.setOnClickListener((android.view.View.OnClickListener) this);
		
		btnCancel=(Button) v.findViewById(R.id.btnCancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
		
		datePicker = (DatePicker) v.findViewById(R.id.datePicker);
		timePicker = (TimePicker) v.findViewById(R.id.timePicker);
		cal = Calendar.getInstance();

		datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DAY_OF_MONTH), null);
		timePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
	}
	
	private void loadMyFriends() {
		final String myUsername = ParseUser.getCurrentUser().getUsername();
		ArrayList<FoodieUser> myFriends = new ArrayList<FoodieUser>();
		adapter = new FoodieUserAdapter(getActivity().getApplicationContext(), myFriends);
		lvFriends.setAdapter(adapter);

		ParseClient.getMyFriends(myUsername, new FindCallback<Friends>() {
	          public void done(List<Friends> results, ParseException e) {
	                if (e == null) {
	                    // Access the array of results here
	                    ArrayList<FoodieUser> allMyFriends = FoodieUser.fromFriendQueryResult(results, myUsername);
	                    Log.d("DEBUG", "Your Friends: " + allMyFriends.get(0) + allMyFriends.get(1));
	                    adapter.addAll(allMyFriends);
	                } else {
	                    Log.d("item", "Error: " + e.getMessage());
	                }
	          }
        });
	}
	
	// To simplify, self implementing OnClickListener
	@Override
	public void onClick(View v) {
		// Get list of selected usernames and store in selectedUsers
		SparseBooleanArray itemSelection = lvFriends.getCheckedItemPositions();
		ArrayList<String> selectedUsers = new ArrayList<String>();
		for (int i = 0; i < itemSelection.size(); i++) {
			if (itemSelection.valueAt(i)) {
				int pos = itemSelection.keyAt(i);
				selectedUsers.add(adapter.getItem(pos).getUsername());
			}
		}
		Log.d("DEBUG", Integer.toString(selectedUsers.size()));
		for (int i = 0; i < selectedUsers.size(); i++) {
			Log.d("DEBUG", "SELECTED: " + selectedUsers.get(i));
		}
		
		// Get the text message
		String msg = etMsg.getText().toString();
		Log.d("DEBUG", "MSG: " + msg);
		
		// Get the selected date&time
		cal.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute(), 00);
		String invitationDate = cal.getTime().toString();
		Log.d("DEBUG", "Date: " + invitationDate);
		
		// compose json invitation
		// TODO: include location
		JSONObject invitation = new JSONObject();
		try {
			invitation.put("date", invitationDate);
			invitation.put("msg", msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// send invitation
		Log.d("DEBUG", invitation.toString());
		if (selectedUsers.size() != 0) {
			ParseClient.pushToUsers(selectedUsers, invitation.toString());
			Toast.makeText(getActivity(), "invites have been sent to users " + selectedUsers.toString(), Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getActivity(), "Please select at least one friend", Toast.LENGTH_SHORT).show();
		}
		dismiss();
		// TODO: close the dialog
		
	}
	
}
