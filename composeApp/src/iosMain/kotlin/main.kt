import androidx.compose.ui.window.ComposeUIViewController
import com.zedsols.ciao_kotlin.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
  App()
}
