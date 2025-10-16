package com.example.taskinspectortest.util

import android.content.Context
import android.widget.Toast

// このクラス内でToastを制御します (例: ActivityやFragment)
class ToastController(private val context: Context?) {

    // 現在表示中の、または最後に作成したToastのインスタンスを保持する
    private var currentToast: Toast? = null

    /**
     * 新しいメッセージをToastで表示します。
     * 以前のToastがあればキャンセルして、新しいものを表示します。
     *
     * @param message 表示するテキスト
     * @param duration 表示時間 ([Toast.LENGTH_SHORT] または [Toast.LENGTH_LONG])
     */
    fun showLatestToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        // 1. 以前のToastがあればキャンセルする
        currentToast?.cancel()

        // 2. 新しいToastを作成し、メンバ変数に保持する
        currentToast = Toast.makeText(context, message, duration)

        // 3. 新しいToastを表示する
        currentToast?.show()
    }
}