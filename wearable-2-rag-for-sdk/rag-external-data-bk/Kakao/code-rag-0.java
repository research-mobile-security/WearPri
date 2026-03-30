// List of the scope IDs that you want to revoke
val scopes = mutableListOf("account_email", "legal_birth_date", "friends")

UserApiClient.instance.revokeScopes(scopes) { scopeInfo, error->
    if (error != null) {
        Log.e(TAG, "Failed to revoke consent.", error)
    }else if (scopeInfo != null) {
        Log.i(TAG, "Succeeded in revoking consent.\n Scopes being used or agreed: $scopeInfo")
    }
}