# portfolio_daily_report_system
Techacademyの最終課題 "日報管理システム" をより拡張したWebシステムのコードです。
ログイン認証(MySQLへの従業員照合)、従業員管理、日報管理、従業員同士のフォロー機能、グループ管理やそれに伴う承認機能と言った要素が含まれています。

# Description
従業員は追加、更新、削除することができます。
追加された従業員は日報を登録・閲覧することができ、ログイン時のトップページには自分の日報一覧が表示されます。
また従業員同士のフォロー機能により、フォローした従業員の日報一覧をタイムラインで見ることもできます。
日報の承認機能のためグループが存在し、配属された従業員は新規登録の際選択した承認先と同じ従業員の日報のみを閲覧、
また承認を行うことができます。

# DEMO

heroku URL: https://daily-report-system-mame.herokuapp.com/

管理者としてのログイン方法

社員番号: admin
パスワード: root

その他従業員としてのログイン方法

社員番号: 従業員管理での社員番号
パスワード: a

※ 操作中に
HTTP Status 500 - org.hibernate.exception.JDBCConnectionException: Unable to acquire JDBC Connection

が出た際はお手数ですがページをリロードして頂くと遷移すると思われます。


# Dependency
- Java SE Development Kit 11
- Eclipse Java EE IDE for Web Developers 4.6.3
- MySQL 5.7
- Apache Maven 4.0
- MySQL JDBC Driver 5.1.45
- Hibernate 5.2.13.Final
- jQuery 3.6.0
