package alejo.meli.utils

import alejo.meli.core.presentation.SafeClickListener
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun View.setSafeOnClickListener(onSafeClick: View.OnClickListener) {
    setOnClickListener(SafeClickListener(onSafeClick = onSafeClick))
}

fun View.show(doShow: Boolean = true) {
    visibility = if (doShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.hide() {
    visibility = View.GONE
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun ImageView.loadImage(url: String?) {
    Picasso.get()
        .load(url)
        .into(this)
}
