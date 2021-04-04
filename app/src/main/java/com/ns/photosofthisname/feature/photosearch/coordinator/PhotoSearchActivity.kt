package com.ns.photosofthisname.feature.photosearch.coordinator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ns.photosofthisname.R
import com.ns.photosofthisname.extension.commitFragment
import com.ns.photosofthisname.navigator.Navigator
import com.ns.photosofthisname.feature.photosearch.view.PhotoSearchFragment
import org.koin.android.ext.android.inject


class PhotoSearchActivity : AppCompatActivity(), IPhotoSearchCoordinator {

    private val navigator: Navigator by inject()

    lateinit var onContactSelected: (contactName: String) -> Unit
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_search)
        commitFragment(PhotoSearchFragment.newInstance())
    }

    override fun showImage(imageUrl: String) {
        navigator.openUrl(this, imageUrl)
    }

    override fun openContactPicker(onContactSelected: (contactName: String) -> Unit) {
        this.onContactSelected = onContactSelected
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
        resultLauncher.launch(intent)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Uri = result.data?.data ?: return@registerForActivityResult

            val projection = arrayOf(CommonDataKinds.Phone.DISPLAY_NAME)
            this.contentResolver?.query(data, projection, null, null, null)?.apply {
                moveToFirst()
                val name = this.getString(this.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                onContactSelected(name)
                close()
            }
        }
    }

}