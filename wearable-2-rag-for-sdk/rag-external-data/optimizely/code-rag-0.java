class CustomUserProfileService : UserProfileService {
   @Throws(Exception::class)
   override fun lookup(userId: String): Map<String, Any>? {
      return null
   }
   @Throws(Exception::class)
   override fun save(userProfile: Map<String, Any>) {
   }
}
val customUserProfileService = CustomUserProfileService()
val optimizelyManager = OptimizelyManager.builder()
         .withSDKKey("<Your_SDK_Key>")
         .withUserProfileService(customUserProfileService)
         .build(context)
