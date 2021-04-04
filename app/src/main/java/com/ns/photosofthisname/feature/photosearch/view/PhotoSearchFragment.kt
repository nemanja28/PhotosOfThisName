package com.ns.photosofthisname.feature.photosearch.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ns.photosofthisname.databinding.FragmentPhotosOfThisNameBinding
import com.ns.photosofthisname.extension.hideKeyboard
import com.ns.photosofthisname.extension.observe
import com.ns.photosofthisname.feature.photosearch.coordinator.PhotoSearchActivity
import com.ns.photosofthisname.feature.photosearch.viewmodel.IPhotoSearchViewModel
import com.ns.photosofthisname.feature.photosearch.viewmodel.PhotoSearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotoSearchFragment : Fragment() {

    //region Architecture Component Properties
    private val photoSearchViewModel: IPhotoSearchViewModel by viewModel<PhotoSearchViewModel>()
    private var coordinator: PhotoSearchActivity? = null
    //endregion

    //region Private Properties
    private var _binding: FragmentPhotosOfThisNameBinding? = null
    private val binding get() = _binding!!
    private var isLoadMode = false
    private lateinit var adapter: PhotosAdapter
    //endregion

    //region Fragment Lifecycle Methods
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PhotoSearchActivity) {
            coordinator = context
        } else {
            throw RuntimeException("$context must implement ${PhotoSearchActivity::class.java.name}")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPhotosOfThisNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    //endregion

    //region Private Methods
    private fun initView() {
        setupRecyclerView()
        binding.searchButton.setOnClickListener {
            setupRecyclerView()
            hideKeyboard()
            photoSearchViewModel.onSearchButtonClick(binding.nameEditText.text.toString())
        }
        binding.pickAContactButton.setOnClickListener {
            coordinator?.openContactPicker {
                setupRecyclerView()
                binding.nameEditText.setText(it)
                photoSearchViewModel.onSearchButtonClick(it)
            }
        }
    }

    private fun setupObservers() {
        observe(photoSearchViewModel.photos) {
            adapter.addData(it)
        }
        observe(photoSearchViewModel.showLoadMoreProgress) {
            isLoadMode = false
            binding.loadProgressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        observe(photoSearchViewModel.showProgressBar) {
            binding.loadProgressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
        observe(photoSearchViewModel.showErrorMessage) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        adapter = PhotosAdapter({
            coordinator?.showImage(it)
        })
        binding.photoList.adapter = adapter
        binding.photoList.addOnScrollListener(photoListScrollListener)
    }
    //endregion

    //region Recycler View Listeners
    private val photoListScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0) {
                val layoutManager = binding.photoList.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                if (!isLoadMode) {
                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                        isLoadMode = true
                        photoSearchViewModel.onScrollToEndOfList()
                    }
                }
            }
        }
    }
    //endregion

    companion object {
        fun newInstance() = PhotoSearchFragment()
    }
}