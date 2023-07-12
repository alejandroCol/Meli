package alejo.meli.home.presentation.detail

import alejo.meli.databinding.ActivityProductDetailBinding
import alejo.meli.home.domain.model.Product
import alejo.meli.home.presentation.adapter.AttributeAdapter
import alejo.meli.home.presentation.search.HomeViewModel.Companion.ARG_PRODUCT
import alejo.meli.utils.hide
import alejo.meli.utils.show
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : AppCompatActivity() {

    private val viewModel: ProductDetailViewModel by viewModels()
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeStates()
        setupList()
        getExtras()
    }

    private fun getExtras() {
        intent.extras?.let {
            it.getSerializable(ARG_PRODUCT)
                ?.let { product -> viewModel.showDetail(product as Product) }
        }
    }

    private fun setupList() {
        binding.rvDetail.apply {
            layoutManager = LinearLayoutManager(this@ProductDetailActivity)
            setHasFixedSize(true)
            adapter = AttributeAdapter()
            addItemDecoration(DividerItemDecoration(context, 0))
        }
    }

    private fun observeStates() {
        viewModel.stateLiveData.observe(this) { handleViewState(it) }
    }

    private fun handleViewState(state: ProductDetailViewState) {
        when (state) {
            is ProductDetailViewState.Content -> showContent(state.detailProduct)
            is ProductDetailViewState.Loading -> showLoading(true)
        }
    }

    private fun showLoading(show: Boolean) {
        binding.run {
            if (show) {
                pbDetail.show()
            } else {
                pbDetail.hide()
            }
        }
    }

    private fun showContent(
        product: Product
    ) {
        binding.run {
            showLoading(false)
            rvDetail.show()
            rvDetail.apply {
                with(adapter as AttributeAdapter) {
                    detailProduct = product
                    product.attributes?.let {
                        attributes = it
                    }
                    notifyDataSetChanged()
                }
            }
        }
    }
}
