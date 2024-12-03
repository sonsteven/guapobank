# GuapoBankTestSuite

## Controllers Tests
### AdminControllerTest
* TestAdminHome
  * Tags: controller, admin, home
  * Ensure that AdminHome view is returned correctly.

* TestViewUserAccounts
  * Tags: controller, admin, user, account
  * Check that user accounts are displayed correctly.

* TestFreezeAccount
  * Tags: controller, admin, account
  * Validate freezing account functionality.

* TestUnfreezeAccount
  * Tags: controller, admin, account
  * Validate unfreezing account functionality.

### HomeControllerTest
* test_index_redirectToLogin
* test_index_redirectToAdminHome
* test_index_redirectToUserHome
* test_login_notLoggedIn
* test_login_redirectToAdminHome
* test_login_redirectToUserHome
* test_authenticate_invalidCredentials
* test_authenticate_adminLogin
* test_authenticate_userLogin
* test_home_redirectToLoginAdmin
* test_home_displayUserHomeWithData
* test_logout_redirectToLogin

### NotificationControllerTest
* test_viewNotifications_loggedIn
* test_viewNotifications_notLoggedIn
* test_markNotificationAsRead_success
* test_markNotificationAsRead_notLoggedIn
* test_markNotificationAsRead_noNotification
* test_markNotificationAsRead_wrongUserNotification

### TransactionControllerTest
* TestShowTransactionForm
* TestCreateTransactionSuccess
* TestCreateTransactionInvalidRecipient
* TestCreateTransactionInvalidSender
* TestCreateTransactionOverBalance
* TestCreateTransactionFrozenAccount
* TestCreateTransactionInvalidTransaction


## Entities Tests
### AccountTest
* testParameterizedConstructor
  * Tags: entities, account, constructor
  * Validate parameterized constructor functionality.
  
* testSetAndGetId
  * Tags: entities, account
  * Validate ID getter and setter.
  
* testSetAndGetAccountType
  * Tags: entities, account
  * Validate account type getter and setter.
  
* TestSetAndGetBalance
  * Tags: entities, account
  * Validate account balance getter and setter.
  
* TestSetAndGetFrozen
  * Tags: entities, account
  * Validate account frozen status getter and setter.
  
* TestSetAndGetInterestRate
  * Tags: entities, account
  * Validate account interest rate getter and setter.
  
* TestSetAndGetMinimumBalance
  * Tags: entities, account
  * Validate account minimum balance getter and setter.
  
* TestSetAndGetOverdraftOptIn
  * Tags: entities, account
  * Validate account overdraft opt in status getter and setter.
  
* TestSetAndGetOverdraftFee
  * Tags: entities, account
  * Validate account overdraft fee getter and setter.
  
* testDailyTransactionLimitForUserTypes
  * Tags: entities, account
  * Validate daily transaction limit for user types.

### NotificationTest
* test_notificationConstructor
* test_notificationParameters
* test_gettersAndSetters
* test_getId

### TransactionTest
* TestGetSetId
  * Tags: entities, transaction
  * Validate getting and setting of transaction ID.
* TestGetSetAmount
  * Tags: entities, transaction
  * Validate getting and setting of transaction amount.
* TestGetTransactionDate
  * Tags: entities, transaction
  * Validate getting of transaction date.
* TestGetSetSenderAccount
  * Tags: entities, transaction
  * Validate getting and setting of transaction sender account.
* TestGetSetRecipientAccount
  * Tags: entities, transaction
  * Validate getting and setting of transaction recipient account.

### UserTest
* TestGetSetId
* TestAddAccount
* TestGetSetAccounts
* TestGetSetPassword
* TestGetSetUsername
* TestGetSetFirstName
* TestGetSetLastName
* TestGetSetRole
* TestIsSetVip
* TestIsSetMilitary
* TestIsSetCorporate


## Services Tests
### AccountServiceImplTest
* TestFindAll
  * Tags: services, accounts
  * Validate finding all accounts.
  
* TestSaveAccount
  * Tags: services, accounts
  * Validate saving an account.
  
* TestFindByUser
  * Tags: services, accounts
  * Validate finding accounts by user.
  
* TestFindById
  * Tags: services, accounts
  * Validate finding accounts by ID.

### InterestServiceImplTest
* TestApplyInterest
  * Tags: services, interest
  * Validate applying interest to an account.
  
* TestApplyInterestToAll
  * Tags: services, interest
  * Validate applying interest to all accounts with interest rate.
  
* TestScheduleInterest
  * Tags: services, interest
  * Validate scheduling interest application.

### NotificationServiceImplTest
* test_sendNotification_success
* test_sendNotification_saveException
* test_getNotificationsForUser_success
* test_getNotificationsForUser_emptyList

### TransactionServiceImplTest
* TestFindAll
  * Tags: services, transaction
  * Validate finding all transactions in the repository.
* TestSave
  * Tags: services, transaction
  * Validate saving a single transaction in the repository.
* TestFindBySenderAccount
  * Tags: services, transaction, account
  * Validate finding a list of transactions sent from a specific account.
* TestFindByRecipientAccount
  * Tags: services, transaction, account
  * Validate finding a list of transactions that a specific account has received.
* TestCalculateTransferFeeAmounts
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on amount transferred.
* TestCalculateTransferFeeCorporate
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on corporate status.
* TestCalculateTransferFeeMilitaryAndVip
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on both military and VIP status.
* TestCalculateTransferFeeMilitary
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on military status.
* TestCalculateTransferFeeVip
  * Tags: services, transaction
  * Validate calculation of the additional transfer fee of a transaction based on VIP status.
* TestIsOverDailyTransactionLimit
  * Tags: services, transaction
  * Validate that the daily transaction limit of a user properly allows/disallows a new transaction.
* TestCreateTransaction
  * Tags: services, transaction
  * Validate the creation of a new transaction.

### UserServiceImplTest
* TestSuccessfulAuthenticate
* TestAuthenticateWrongPassword
* TestAuthenticateInvalidUser
* TestFindAll
* TestSave
* TestFindByIdSuccess


## SeleniumTesting
* test_page_load
  * Tags: selenium, page
  * Validate page loading functionality.
  
* test_login_user
  * Tags: selenium, page
  * Validate admin login functionality.
  
* test_login_admin
  * Tags: selenium, page
  * Validate logging in as an admin.
  
* test_login_invalid_password
  * Tags: selenium, page
  * Validate behavior for invalid password login.
  
* test_view_notifications
  * Tags: selenium, page
  * Validate viewing notifications inbox.
  
* test_user_logout
  * Tags: selenium, logout, user
  * Validate user logout functionality.
  
* test_admin_logout
  * Tags: selenium, logout, admin
  * Validate admin logout functionality.
  
* test_view_accounts
  * Tags: selenium, accounts
  * Validate viewing a users accounts.
  
* test_freeze_and_unfreeze_account
  * Tags: selenium, accounts, freeze, unfreeze
  * Validate freezing and unfreezing an account.
  
* test_create_transaction
  * Tags: selenium, account, transaction
  * Validate creating a transaction successfully.
  
* test_invalid_transaction_missing_account
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction when the sender or recipient account is missing.
  
* test_invalid_transaction_insufficient_balance
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction for an account with insufficient balance and no overdraft enabled.
  
* test_invalid_transaction_exceeds_minimum_balance
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction for an account with amount exceeding the account minimum balance.
  
* test_invalid_transaction_frozen_account
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction for a frozen account.
  
* test_invalid_transaction_exceed_daily_limit
  * Tags: selenium, account, transaction
  * Validate behavior for creating a transaction when the account has reached its daily limit.