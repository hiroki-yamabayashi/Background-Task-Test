# Background-Task-Test
Android StudioのBackground Task Inspector動作確認用のコード

Geminiに書いてもらったコードです<br>
本プロジェクトを利用したことで生じた損害に対して、当方は一切の責任を負いかねます🙇

## アラーム
アラーム機能については端末設定が必要になります<br>
初期状態のままでは利用できない恐れがあります

- `Alarm Set: RTC_WAKEUP (Exact)`というメッセージが表示されればアラームは適切にセットされています
- `Cannot schedule exact alarms. Check permissions/settings.`というメッセージが表示されたら設定が必要です

ボタン押下すると自動的に端末設定に遷移しますので、アプリを選択して権限を付与してください
<video src=https://github.com/user-attachments/assets/7fc80da3-322a-42ec-acb6-08d4aa320333>
