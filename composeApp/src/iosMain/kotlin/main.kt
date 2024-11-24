import androidx.compose.ui.window.ComposeUIViewController
import com.zedsols.ciao_kotlin.App
import platform.UIKit.UIViewController

fun makeComposeUIViewController(): UIViewController = ComposeUIViewController {
  App()
}
