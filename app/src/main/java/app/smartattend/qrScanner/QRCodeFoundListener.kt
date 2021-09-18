package app.smartattend.qrScanner

interface QRCodeFoundListener {
    fun onQRCodeFound(qrCode: String?)
    fun qrCodeNotFound()
}