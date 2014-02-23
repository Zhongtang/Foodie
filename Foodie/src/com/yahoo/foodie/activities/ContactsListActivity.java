package com.yahoo.foodie.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.yahoo.foodie.R;
import com.yahoo.foodie.fragments.ContactsListFragment;

public class ContactsListActivity extends FragmentActivity implements
		ContactsListFragment.OnContactsInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_list);
		overridePendingTransition(R.anim.anim_slide_in_bottom,
				R.anim.anim_slide_out_top);

		if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {

			String searchQuery = getIntent()
					.getStringExtra(SearchManager.QUERY);
			ContactsListFragment mContactsListFragment = (ContactsListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.contact_list);

			mContactsListFragment.setSearchQuery(searchQuery);

			// Set special title for search results
			String title = getString(
					R.string.contacts_list_search_results_title, searchQuery);
			setTitle(title);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		overridePendingTransition(R.anim.anim_slide_in_top,
				R.anim.anim_slide_out_bottom);
	}

	/**
	 * @param contactUri
	 *            The contact Uri to the selected contact.
	 */
	@Override
	public void onContactSelected(Uri contactUri) {
		// Placeholder for InvitationActivity
		// Intent intent = new Intent(this, InvitationActivity.class);
		// intent.setData(contactUri);
		// startActivity(intent);
	}

	@Override
	public void onSelectionCleared() {
		// this.setContact(null);
	}
}
