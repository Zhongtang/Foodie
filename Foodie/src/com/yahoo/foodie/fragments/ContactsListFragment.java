package com.yahoo.foodie.fragments;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.QuickContactBadge;
import android.widget.SearchView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yahoo.foodie.BuildConfig;
import com.yahoo.foodie.R;
import com.yahoo.foodie.utils.ImageLoader;

public class ContactsListFragment extends ListFragment implements
		AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = "ContactsListFragment";
	private static final String STATE_PREVIOUSLY_SELECTED_KEY = "com.yahoo.foodie.activities.SELECTED_ITEM";
	private ContactsAdapter mAdapter;
	private ImageLoader mImageLoader;
	private String mSearchTerm;
	private OnContactsInteractionListener mOnContactSelectedListener;
	private int mPreviouslySelectedSearchItem = 0;
	private boolean mSearchQueryChanged;
	private boolean mIsSearchResultView = false;

	/**
	 * Fragments require an empty constructor.
	 */
	public ContactsListFragment() {
	}

	/**
	 * @param query
	 *            The contacts search query.
	 */
	public void setSearchQuery(String query) {
		if (TextUtils.isEmpty(query)) {
			mIsSearchResultView = false;
		} else {
			mSearchTerm = query;
			mIsSearchResultView = true;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setHasOptionsMenu(true);
		mAdapter = new ContactsAdapter(getActivity());
		if (savedInstanceState != null) {
			mSearchTerm = savedInstanceState.getString(SearchManager.QUERY);
			mPreviouslySelectedSearchItem = savedInstanceState.getInt(
					STATE_PREVIOUSLY_SELECTED_KEY, 0);
		}

		mImageLoader = new ImageLoader(getActivity(),
				getListPreferredItemHeight()) {
			@Override
			protected Bitmap processBitmap(Object data) {
				return loadContactPhotoThumbnail((String) data, getImageSize());
			}
		};

		mImageLoader.setLoadingImage(R.drawable.ic_contact_picture_holo_light);
		mImageLoader.addImageCache(getActivity().getSupportFragmentManager(),
				0.1f);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.contact_list_fragment, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setListAdapter(mAdapter);
		getListView().setOnItemClickListener(this);
		getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView absListView,
					int scrollState) {
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
					mImageLoader.setPauseWork(true);
				} else {
					mImageLoader.setPauseWork(false);
				}
			}

			@Override
			public void onScroll(AbsListView absListView, int i, int i1, int i2) {
			}
		});

		if (mPreviouslySelectedSearchItem == 0) {
			getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mOnContactSelectedListener = (OnContactsInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnContactsInteractionListener");
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		mImageLoader.setPauseWork(false);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		final Cursor cursor = mAdapter.getCursor();
		cursor.moveToPosition(position);
		final Uri uri = Contacts.getLookupUri(cursor.getLong(ContactsQuery.ID),
				cursor.getString(ContactsQuery.LOOKUP_KEY));
		mOnContactSelectedListener.onContactSelected(uri);
	}

	/**
	 * Called when ListView selection is cleared, for example when search mode
	 * is finished and the currently selected contact should no longer be
	 * selected.
	 */
	private void onSelectionCleared() {
		mOnContactSelectedListener.onSelectionCleared();
		getListView().clearChoices();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.contacts_list, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		if (mIsSearchResultView) {
			searchItem.setVisible(false);
		}

		final SearchManager searchManager = (SearchManager) getActivity()
				.getSystemService(Context.SEARCH_SERVICE);

		final SearchView searchView = (SearchView) searchItem.getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getActivity().getComponentName()));

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String queryText) {
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				String newFilter = !TextUtils.isEmpty(newText) ? newText : null;
				if (mSearchTerm == null && newFilter == null) {
					return true;
				}

				if (mSearchTerm != null && mSearchTerm.equals(newFilter)) {
					return true;
				}

				mSearchTerm = newFilter;
				mSearchQueryChanged = true;
				getLoaderManager().restartLoader(ContactsQuery.QUERY_ID, null,
						ContactsListFragment.this);
				return true;
			}
		});

		searchItem
				.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
					@Override
					public boolean onMenuItemActionExpand(MenuItem menuItem) {
						return true;
					}

					@Override
					public boolean onMenuItemActionCollapse(MenuItem menuItem) {
						if (!TextUtils.isEmpty(mSearchTerm)) {
							onSelectionCleared();
						}
						mSearchTerm = null;
						getLoaderManager().restartLoader(
								ContactsQuery.QUERY_ID, null,
								ContactsListFragment.this);
						return true;
					}
				});

		if (mSearchTerm != null) {
			final String savedSearchTerm = mSearchTerm;
			searchItem.expandActionView();
			searchView.setQuery(savedSearchTerm, false);
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (!TextUtils.isEmpty(mSearchTerm)) {
			outState.putString(SearchManager.QUERY, mSearchTerm);
			outState.putInt(STATE_PREVIOUSLY_SELECTED_KEY, getListView()
					.getCheckedItemPosition());
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menu_search:
			getActivity().onSearchRequested();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {

		if (id == ContactsQuery.QUERY_ID) {
			Uri contentUri;

			if (mSearchTerm == null) {
				contentUri = ContactsQuery.CONTENT_URI;
			} else {
				contentUri = Uri.withAppendedPath(ContactsQuery.FILTER_URI,
						Uri.encode(mSearchTerm));
			}
			return new CursorLoader(getActivity(), contentUri,
					ContactsQuery.PROJECTION, ContactsQuery.SELECTION, null,
					ContactsQuery.SORT_ORDER);
		}

		Log.e(TAG, "onCreateLoader - incorrect ID provided (" + id + ")");
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

		if (loader.getId() == ContactsQuery.QUERY_ID) {
			mAdapter.swapCursor(data);

			if (!TextUtils.isEmpty(mSearchTerm) && mSearchQueryChanged) {
				if (data != null
						&& data.moveToPosition(mPreviouslySelectedSearchItem)) {

					final Uri uri = Uri.withAppendedPath(Contacts.CONTENT_URI,
							String.valueOf(data.getLong(ContactsQuery.ID)));
					mOnContactSelectedListener.onContactSelected(uri);
					getListView().setItemChecked(mPreviouslySelectedSearchItem,
							true);
				} else {
					onSelectionCleared();
				}

				mPreviouslySelectedSearchItem = 0;
				mSearchQueryChanged = false;
			}
		}
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (loader.getId() == ContactsQuery.QUERY_ID) {
			mAdapter.swapCursor(null);
		}
	}

	/**
	 * Gets the preferred height for each item in the ListView, in pixels, after
	 * accounting for screen density. ImageLoader uses this value to resize
	 * thumbnail images to match the ListView item height.
	 * 
	 * @return The preferred height in pixels, based on the current theme.
	 */
	private int getListPreferredItemHeight() {
		final TypedValue typedValue = new TypedValue();

		getActivity().getTheme().resolveAttribute(
				android.R.attr.listPreferredItemHeight, typedValue, true);

		final DisplayMetrics metrics = new android.util.DisplayMetrics();

		getActivity().getWindowManager().getDefaultDisplay()
				.getMetrics(metrics);

		return (int) typedValue.getDimension(metrics);
	}

	/**
	 * Decodes and scales a contact's image from a file pointed to by a Uri in
	 * the contact's data, and returns the result as a Bitmap. The column that
	 * contains the Uri varies according to the platform version.
	 * 
	 * @param photoData
	 *            For platforms prior to Android 3.0, provide the Contact._ID
	 *            column value. For Android 3.0 and later, provide the
	 *            Contact.PHOTO_THUMBNAIL_URI value.
	 * @param imageSize
	 *            The desired target width and height of the output image in
	 *            pixels.
	 * @return A Bitmap containing the contact's image, resized to fit the
	 *         provided image size. If no thumbnail exists, returns null.
	 */
	private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {

		if (!isAdded() || getActivity() == null) {
			return null;
		}

		AssetFileDescriptor afd = null;

		try {
			Uri thumbUri;

			final Uri contactUri = Uri.withAppendedPath(Contacts.CONTENT_URI,
					photoData);
			thumbUri = Uri
					.withAppendedPath(contactUri, Photo.CONTENT_DIRECTORY);
			afd = getActivity().getContentResolver().openAssetFileDescriptor(
					thumbUri, "r");
			FileDescriptor fileDescriptor = afd.getFileDescriptor();

			if (fileDescriptor != null) {
				return ImageLoader.decodeSampledBitmapFromDescriptor(
						fileDescriptor, imageSize, imageSize);
			}
		} catch (FileNotFoundException e) {
			if (BuildConfig.DEBUG) {
				Log.d(TAG, "Contact photo thumbnail not found for contact "
						+ photoData + ": " + e.toString());
			}
		} finally {

			if (afd != null) {
				try {
					afd.close();
				} catch (IOException e) {

				}
			}
		}

		return null;
	}

	/**
	 * This is a subclass of CursorAdapter that supports binding Cursor columns
	 * to a view layout. If those items are part of search results, the search
	 * string is marked by highlighting the query text. An
	 * {@link AlphabetIndexer} is used to allow quicker navigation up and down
	 * the ListView.
	 */
	private class ContactsAdapter extends CursorAdapter implements
			SectionIndexer {
		private LayoutInflater mInflater;
		private AlphabetIndexer mAlphabetIndexer;
		private TextAppearanceSpan highlightTextSpan;

		/**
		 * Instantiates a new Contacts Adapter.
		 * 
		 * @param context
		 *            A context that has access to the app's layout.
		 */
		public ContactsAdapter(Context context) {
			super(context, null, 0);
			mInflater = LayoutInflater.from(context);
			final String alphabet = context.getString(R.string.alphabet);
			mAlphabetIndexer = new AlphabetIndexer(null,
					ContactsQuery.SORT_KEY, alphabet);
			highlightTextSpan = new TextAppearanceSpan(getActivity(),
					R.style.searchTextHiglight);
		}

		/**
		 * Identifies the start of the search string in the display name column
		 * of a Cursor row. E.g. If displayName was "Adam" and search query
		 * (mSearchTerm) was "da" this would return 1.
		 * 
		 * @param displayName
		 *            The contact display name.
		 * @return The starting position of the search string in the display
		 *         name, 0-based. The method returns -1 if the string is not
		 *         found in the display name, or if the search string is empty
		 *         or null.
		 */
		private int indexOfSearchQuery(String displayName) {
			if (!TextUtils.isEmpty(mSearchTerm)) {
				return displayName.toLowerCase(Locale.getDefault()).indexOf(
						mSearchTerm.toLowerCase(Locale.getDefault()));
			}
			return -1;
		}

		/**
		 * Overrides newView() to inflate the list item views.
		 */
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

			final View itemLayout = mInflater.inflate(
					R.layout.contact_list_item, viewGroup, false);
			final ViewHolder holder = new ViewHolder();
			holder.text1 = (TextView) itemLayout
					.findViewById(android.R.id.text1);
			holder.text2 = (TextView) itemLayout
					.findViewById(android.R.id.text2);
			holder.icon = (QuickContactBadge) itemLayout
					.findViewById(android.R.id.icon);
			itemLayout.setTag(holder);

			return itemLayout;
		}

		/**
		 * Binds data from the Cursor to the provided view.
		 */
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final ViewHolder holder = (ViewHolder) view.getTag();
			final String photoUri = cursor
					.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
			final String displayName = cursor
					.getString(ContactsQuery.DISPLAY_NAME);
			final int startIndex = indexOfSearchQuery(displayName);

			if (startIndex == -1) {
				holder.text1.setText(displayName);
				if (TextUtils.isEmpty(mSearchTerm)) {
					holder.text2.setVisibility(View.GONE);
				} else {
					holder.text2.setVisibility(View.VISIBLE);
				}
			} else {
				final SpannableString highlightedName = new SpannableString(
						displayName);
				highlightedName.setSpan(highlightTextSpan, startIndex,
						startIndex + mSearchTerm.length(), 0);
				holder.text1.setText(highlightedName);
				holder.text2.setVisibility(View.GONE);
			}

			final Uri contactUri = Contacts.getLookupUri(
					cursor.getLong(ContactsQuery.ID),
					cursor.getString(ContactsQuery.LOOKUP_KEY));
			holder.icon.assignContactUri(contactUri);
			mImageLoader.loadImage(photoUri, holder.icon);
		}

		/**
		 * Overrides swapCursor to move the new Cursor into the AlphabetIndex as
		 * well as the CursorAdapter.
		 */
		@Override
		public Cursor swapCursor(Cursor newCursor) {
			mAlphabetIndexer.setCursor(newCursor);
			return super.swapCursor(newCursor);
		}

		/**
		 * An override of getCount that simplifies accessing the Cursor. If the
		 * Cursor is null, getCount returns zero. As a result, no test for
		 * Cursor == null is needed.
		 */
		@Override
		public int getCount() {
			if (getCursor() == null) {
				return 0;
			}
			return super.getCount();
		}

		/**
		 * Defines the SectionIndexer.getSections() interface.
		 */
		@Override
		public Object[] getSections() {
			return mAlphabetIndexer.getSections();
		}

		/**
		 * Defines the SectionIndexer.getPositionForSection() interface.
		 */
		@Override
		public int getPositionForSection(int i) {
			if (getCursor() == null) {
				return 0;
			}
			return mAlphabetIndexer.getPositionForSection(i);
		}

		/**
		 * Defines the SectionIndexer.getSectionForPosition() interface.
		 */
		@Override
		public int getSectionForPosition(int i) {
			if (getCursor() == null) {
				return 0;
			}
			return mAlphabetIndexer.getSectionForPosition(i);
		}

		/**
		 * A class that defines fields for each resource ID in the list item
		 * layout. This allows ContactsAdapter.newView() to store the IDs once,
		 * when it inflates the layout, instead of calling findViewById in each
		 * iteration of bindView.
		 */
		private class ViewHolder {
			TextView text1;
			TextView text2;
			QuickContactBadge icon;
		}
	}

	/**
	 * This interface must be implemented by any activity that loads this
	 * fragment. When an interaction occurs, such as touching an item from the
	 * ListView, these callbacks will be invoked to communicate the event back
	 * to the activity.
	 */
	public interface OnContactsInteractionListener {
		/**
		 * Called when a contact is selected from the ListView.
		 * 
		 * @param contactUri
		 *            The contact Uri.
		 */
		public void onContactSelected(Uri contactUri);

		/**
		 * Called when the ListView selection is cleared like when a contact
		 * search is taking place or is finishing.
		 */
		public void onSelectionCleared();
	}

	/**
	 * This interface defines constants for the Cursor and CursorLoader, based
	 * on constants defined in the
	 * {@link android.provider.ContactsContract.Contacts} class.
	 */
	public interface ContactsQuery {
		final static int QUERY_ID = 1;
		final static Uri CONTENT_URI = Contacts.CONTENT_URI;
		final static Uri FILTER_URI = Contacts.CONTENT_FILTER_URI;
		@SuppressLint("InlinedApi")
		final static String SELECTION = (Contacts.DISPLAY_NAME_PRIMARY)
				+ "<>''" + " AND " + Contacts.IN_VISIBLE_GROUP + "=1";
		@SuppressLint("InlinedApi")
		final static String SORT_ORDER = Contacts.SORT_KEY_PRIMARY;
		@SuppressLint("InlinedApi")
		final static String[] PROJECTION = {
		Contacts._ID,
		Contacts.LOOKUP_KEY,
		Contacts.DISPLAY_NAME_PRIMARY,
		Contacts.PHOTO_THUMBNAIL_URI,
		SORT_ORDER, };

		final static int ID = 0;
		final static int LOOKUP_KEY = 1;
		final static int DISPLAY_NAME = 2;
		final static int PHOTO_THUMBNAIL_DATA = 3;
		final static int SORT_KEY = 4;
	}
}
