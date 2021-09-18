package app.smartattend.qrScanner
//
//import android.graphics.ImageFormat.*
//import androidx.camera.core.ImageAnalysis
//import androidx.camera.core.ImageProxy
//import com.google.zxing.*
//import com.google.zxing.common.HybridBinarizer
//import java.nio.ByteBuffer
//
//
//class QRCodeImageAnalyzer (private val qrCodeFoundListener: QRCodeFoundListener) : ImageAnalysis.Analyzer{
//    private val yuvFormats = mutableListOf(YUV_420_888, YUV_422_888, YUV_444_888)
//    private val reader = MultiFormatReader().apply {
//        val map = mapOf(
//                DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
//        )
//        setHints(map)
//    }
//    override fun analyze(image: ImageProxy) {
//        if (image.format in yuvFormats) {
//            val data = image.planes[0].buffer.toByteArray()
//            val source = PlanarYUVLuminanceSource(
//                    data,
//                    image.width, image.height,
//                    0, 0,
//                    image.width, image.height,
//                    false
//            )
//            val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
//            try {
//                val result = reader.decode(binaryBitmap)
//                qrCodeFoundListener.onQRCodeFound(result.text)
//            } catch (e: FormatException) {
//                qrCodeFoundListener.qrCodeNotFound()
//            } catch (e: ChecksumException) {
//                qrCodeFoundListener.qrCodeNotFound()
//            } catch (e: NotFoundException) {
//                qrCodeFoundListener.qrCodeNotFound()
//            }
//            image.close()
//        }
//    }
//
//}
//
//private fun ByteBuffer.toByteArray(): ByteArray{
//    rewind()
//    val data = ByteArray(remaining())
//    get(data)
//    return data
//}