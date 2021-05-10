# portfolio_daily_report_system
Techacademyの最終課題 "日報管理システム" をより拡張したWebシステムのコードです。
ログイン認証(MySQLへの従業員照合)、従業員管理、日報管理、従業員同士のフォロー機能、グループ管理やそれに伴う承認機能と言った要素が含まれています。

# Description
従業員は追加、更新、削除することができます。
追加された従業員は日報を登録・閲覧することができ、ログイン時のトップページには自分の日報一覧が表示されます。
また従業員同士のフォロー機能により、フォローした従業員の日報一覧をタイムラインで見ることもできます。
日報の承認機能のためグループが存在し、配属された従業員は新規登録の際選択した承認先と同じ従業員の日報のみを閲覧、
また承認を行うことができます。
（承認機能に関しては現在実装中です）