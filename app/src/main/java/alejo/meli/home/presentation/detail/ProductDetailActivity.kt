package alejo.meli.home.presentation.detail

import alejo.meli.databinding.ActivityProductDetailBinding
import alejo.meli.home.domain.model.Product
import alejo.meli.home.presentation.search.HomeViewModel.Companion.ARG_PRODUCT
import alejo.meli.utils.hide
import alejo.meli.utils.loadImage
import alejo.meli.utils.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        getExtras()
    }

    private fun getExtras() {
        intent.extras?.let {
            it.getSerializable(ARG_PRODUCT)?.let { product -> viewModel.showDetail(product as Product) }
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
                clProductDetail.hide()
            } else {
                pbDetail.hide()
                clProductDetail.show()
            }
        }
    }

    private fun showContent(
        detailProduct: Product
    ) {
        binding.run {
            showLoading(false)
            tvTitle.text = detailProduct.title
            ivProduct.loadImage(detailProduct.thumbnail)
            tvPrice.text = "$"+detailProduct.price
        }
    }
}
