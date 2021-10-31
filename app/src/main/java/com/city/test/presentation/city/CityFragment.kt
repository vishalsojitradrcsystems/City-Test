package com.city.test.presentation.city

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.city.test.R
import com.city.test.data.source.local.AppDatabase
import com.city.test.databinding.FragmentCitiesBinding
import com.city.test.databinding.HolderCityBinding
import com.city.test.domain.model.City
import com.city.test.domain.model.Coord
import com.city.test.util.RecyclerViewBuilder_Binding
import com.city.test.util.RecyclerViewLayoutManager.LINEAR
import com.city.test.util.RecyclerViewLinearLayout.VERTICAL
import com.city.test.util.readJsonFromAssets
import com.city.test.util.setUpRecyclerView_Binding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class CityFragment : Fragment() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private lateinit var fragmentCitiesBinding: FragmentCitiesBinding
    private var adapter: RecyclerViewBuilder_Binding<City, HolderCityBinding>? = null

    private val viewModel: CityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAlbums()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentCitiesBinding = FragmentCitiesBinding.inflate(inflater)
        fragmentCitiesBinding.vm = viewModel
        fragmentCitiesBinding.lifecycleOwner = this
        setupObserver()
        setupSearchView()
        return fragmentCitiesBinding.root
    }

    private fun setupSearchView() {
        fragmentCitiesBinding.searchView.setOnCloseListener {
            fragmentCitiesBinding.searchView.clearFocus()
            fragmentCitiesBinding.root.requestFocus()
            false
        }
        fragmentCitiesBinding.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    viewModel.loadAlbums()
                } else {
                    viewModel.loadSearchCities(newText)
                }
                return false
            }

        })
    }

    private fun setupObserver() {
        viewModel.albumsReceivedLiveData.observe(viewLifecycleOwner, { list ->
            if (list.isNullOrEmpty() and !viewModel.isFromSearch) {
                try {
                    viewModel.isLoad.value = false
                    lifecycleScope.launch {
                        val data = requireContext().readJsonFromAssets("cities.json")
                        data?.let {
                            Gson().fromJson<List<City>>(
                                it,
                                object : TypeToken<List<City>>() {}.type
                            )
                        }
                            ?.let {
                                viewModel.getAlbumListUseCase.saveCities(it)
                            }
                    }.invokeOnCompletion { viewModel.loadAlbums() }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                setupRecyclerView(list)
            }
        })
    }

    //    cities.sortedWith(
//    compareBy(
//    String.CASE_INSENSITIVE_ORDER,
//    { it?.getDisplayName().toString() })
//    )
    private fun setupRecyclerView(cities: List<City>) {
        viewModel.isEmpty.value = cities.isNullOrEmpty()
        adapter =
            fragmentCitiesBinding.albumsRecyclerView.setUpRecyclerView_Binding(
                R.layout.holder_city,
                ArrayList(
                    cities
                ),
                LINEAR,
                VERTICAL
            ) {
                contentBinder { item, binding, _ ->
                    binding.city = item
                    binding.root.setOnClickListener {
                        navigateToMap(item.coord)
                    }
                }
            }
    }

    private fun navigateToMap(coord: Coord?) {
        try {
            val mapUri: Uri = Uri.parse("geo:0,0?q=${coord?.lat},${coord?.lon}")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //TODO Replace your google map key in string resource "google_map_key" and uncommnet below code to load map within application
//        findNavController().navigate(R.id.action_fragment_city_to_fragment_map, Bundle().apply {
//            putString(
//                MapFragment.KEY_ALBUM_ID, Gson().toJson(coord)
//            )
//        })
    }


    override fun onDetach() {
        super.onDetach()
        adapter = null
    }

}