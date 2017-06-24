package com.bobble.bobblesampleapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by amitshekhar on 26/04/16.
 */
public final class BobblePrefs extends SharedPreferencesHelper {

    public BobblePrefs(Context context) {
        super(context.getSharedPreferences("BobblePrefs", 0));
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("BobblePrefs", 0);
    }

    public StringPrefField gcmId() {
        return stringField("gcmId", "");
    }

    public StringPrefField deviceId() {
        return stringField("deviceId", "");
    }

    public IntPrefField appVersion() {
        return intField("appVersion", 0);
    }

    public LongPrefField timeOfFirstLaunch() {
        return longField("timeOfFirstLaunch", 0L);
    }

    public StringPrefField deviceInfo() {
        return stringField("deviceInfo", "");
    }

    public StringPrefField userTimeZone() {
        return stringField("userTimeZone", "");
    }

    public BooleanPrefField isRegistered() {
        return booleanField("isRegistered", false);
    }

    public BooleanPrefField isGcmSent() {
        return booleanField("isGcmSent", false);
    }

    public BooleanPrefField isContactsSent() {
        return booleanField("isContactsSent", false);
    }

    public StringPrefField userId() {
        return stringField("userId", "");
    }

    public IntPrefField numberOfBobbleCreated() {
        return intField("numberOfBobbleCreated", 0);
    }

    public BooleanPrefField isFeedbackSent() {
        return booleanField("isFeedbackSent", false);
    }

    public BooleanPrefField isRatedOnPlayStore() {
        return booleanField("isRatedOnPlayStore", false);
    }

    public IntPrefField screenWidth() {
        return intField("screenWidth", 0);
    }

    public IntPrefField screenHeight() {
        return intField("screenHeight", 0);
    }

    public StringPrefField privateDirectory() {
        return stringField("privateDirectory", "");
    }

    public StringPrefField sharingDirectory() {
        return stringField("sharingDirectory", "");
    }

    public StringPrefField publicDirectory() {
        return stringField("publicDirectory", "");
    }

    public IntPrefField mobileDpiCategory() {
        return intField("mobileDpiCategory", 0);
    }

    public IntPrefField deviceHeight() {
        return intField("deviceHeight", 0);
    }

    public IntPrefField deviceWidth() {
        return intField("deviceWidth", 0);
    }

    public IntPrefField appOpenedCount() {
        return intField("appOpenedCount", 0);
    }

    public IntPrefField appOpenedCountVersionSpecific() {
        return intField("appOpenedCountVersionSpecific", 0);
    }

    public StringPrefField installReferral() {
        return stringField("installReferral", "");
    }

    public StringPrefField utmCampaign() {
        return stringField("utmCampaign", "");
    }

    public StringPrefField urlForReferral() {
        return stringField("urlForReferral", "");
    }

    public StringPrefField referralCategory() {
        return stringField("referralCategory", "");
    }

    public StringPrefField tabName() {
        return stringField("tabName", "");
    }

    public BooleanPrefField isWorkDoneForReferral() {
        return booleanField("isWorkDoneForReferral", false);
    }

    public StringPrefField branchUtmMedium() {
        return stringField("branchUtmMedium", "");
    }

    public StringPrefField branchUtmCampaign() {
        return stringField("branchUtmCampaign", "");
    }

    public StringPrefField branchUtmTerm() {
        return stringField("branchUtmTerm", "");
    }

    public StringPrefField branchUtmContent() {
        return stringField("branchUtmContent", "");
    }

    public StringPrefField branchUtmSource() {
        return stringField("branchUtmSource", "");
    }

    public BooleanPrefField isWorkDoneForBranchReferral() {
        return booleanField("isWorkDoneForBranchReferral", false);
    }

    public IntPrefField singleBackgroundResourceHeight() {
        return intField("singleBackgroundResourceHeight", 1624);
    }

    public IntPrefField doubleBackgroundResourceHeight() {
        return intField("doubleBackgroundResourceHeight", 812);
    }

    public IntPrefField backgroundResourceWidth() {
        return intField("backgroundResourceWidth", 0);
    }

    public IntPrefField actualAppVersionCodeAvailableOnPlayStore() {
        return intField("actualAppVersionCodeAvailableOnPlayStore", 0);
    }

    public LongPrefField selectedCharacterId() {
        return longField("selectedCharacterId", 7L);
    }

    public BooleanPrefField imageSampling() {
        return booleanField("imageSampling", false);
    }

    public BooleanPrefField faceImageSampling() {
        return booleanField("faceImageSampling", false);
    }

    public IntPrefField bobbleSaveShare() {
        return intField("bobbleSaveShare", 0);
    }

    public IntPrefField storySaveShare() {
        return intField("storySaveShare", 0);
    }

    public IntPrefField stickerSaveShare() {
        return intField("stickerSaveShare", 0);
    }

    public IntPrefField lastRatingGiven() {
        return intField("lastRatingGiven", 0);
    }

    public IntPrefField lastRatingDialogShownInVersion() {
        return intField("lastRatingDialogShownInVersion", 0);
    }

    public LongPrefField lastRatingDialogTime() {
        return longField("lastRatingDialogTime", 0L);
    }

    public StringPrefField mobileDpiString() {
        return stringField("mobileDpiString", "xhdpi");
    }

    public StringPrefField inviteCampaignName() {
        return stringField("inviteCampaignName", "default");
    }

    public StringPrefField inviteCampaignImageUrl() {
        return stringField("inviteCampaignImageUrl", "");
    }

    public IntPrefField inviteCampaignCurrentInstalls() {
        return intField("inviteCampaignCurrentInstalls", 0);
    }

    public IntPrefField inviteCampaignMaximumInstalls() {
        return intField("inviteCampaignMaximumInstalls", 10);
    }

    public StringPrefField inviteCampaignTitle() {
        return stringField("inviteCampaignTitle", "YOU DISCOVERED US!");
    }

    public StringPrefField inviteCampaignBody() {
        return stringField("inviteCampaignBody", "Help your friends to discover Bobble app");
    }

    public StringPrefField inviteCampaignTermsTitle() {
        return stringField("inviteCampaignTermsTitle", "Bobble App");
    }

    public StringPrefField inviteCampaignTermsBody() {
        return stringField("inviteCampaignTermsBody", "Have Fun Making Stickers and Comics");
    }

    public StringPrefField inviteCampaignShareLink() {
        return stringField("inviteCampaignShareLink", "http://bobble.in/appinvite");
    }

    public StringPrefField inviteCampaignCompletionMessage() {
        return stringField("inviteCampaignCompletionMessage", "");
    }

    public BooleanPrefField isTimelineToShow() {
        return booleanField("isTimelineToShow", false);
    }

    public BooleanPrefField isNumberOfInstallsToShow() {
        return booleanField("isNumberOfInstallsToShow", false);
    }

    public StringPrefField inviteCampaignPopupMessage() {
        return stringField("inviteCampaignPopupMessage", "Now help your friends to discover Bobble app");
    }

    public StringPrefField inviteCampaignPopupImageUrl() {
        return stringField("inviteCampaignPopupImageUrl", "");
    }

    public BooleanPrefField isInvitePopupToShow() {
        return booleanField("isInvitePopupToShow", true);
    }

    public IntPrefField invitePopupMinAppLaunchCountToShow() {
        return intField("invitePopupMinAppLaunchCountToShow", 5);
    }

    public IntPrefField invitePopupNumberOfTimesToShow() {
        return intField("invitePopupNumberOfTimesToShow", 5);
    }

    public IntPrefField invitePopupNumberOfDaysIntervalToShow() {
        return intField("invitePopupNumberOfDaysIntervalToShow", 1);
    }

    public BooleanPrefField isNewInstallPopupToShow() {
        return booleanField("isNewInstallPopupToShow", false);
    }

    public BooleanPrefField isInviteSubmitFormToShow() {
        return booleanField("isInviteSubmitFormToShow", false);
    }

    public StringPrefField newInstallsPopupImageUrl() {
        return stringField("newInstallsPopupImageUrl", "");
    }

    public StringPrefField newInstallsPopupText() {
        return stringField("newInstallsPopupText", "You have got new installs");
    }

   /* public StringPrefField inviteShareMessage() {
        return stringField("inviteShareMessage", "Must try #BobbleKeyboard \uD83D\uDE04. It creates custom stickers with your face & you can тYρE in " + FontsMapper.getInstance().getNameWithFont("COOL", Constants.FONT12) + " " + FontsMapper.getInstance().getNameWithFont("FONTS", Constants.FONT6) + ". Get @BobbleApp http://MakeMyBobble.in/appinvite");
    }*/

    public IntPrefField newInstallPopupNumbersOfTimesToShow() {
        return intField("newInstallPopupNumbersOfTimesToShow", 10);
    }

    public IntPrefField invitePopupCancelRetryAppLaunchCount() {
        return intField("invitePopupCancelRetryAppLaunchCount", 5);
    }

    public IntPrefField invitePopupCancelRetryNumberOfDaysCount() {
        return intField("invitePopupCancelRetryNumberOfDaysCount", 5);
    }

    public StringPrefField inviteCampaignNameOld() {
        return stringField("inviteCampaignNameOld", "");
    }

    public BooleanPrefField popupShownOnThatDay() {
        return booleanField("popupShownOnThatDay", false);
    }

    public IntPrefField appLaunchInviteCounter() {
        return intField("appLaunchInviteCounter", 0);
    }

    public IntPrefField appOpenInviteCounter() {
        return intField("appOpenInviteCounter", 0);
    }

    public IntPrefField invitePreviousInstallCounter() {
        return intField("invitePreviousInstallCounter", 0);
    }

    public IntPrefField invitePopupNumberOfTimesShown() {
        return intField("invitePopupNumberOfTimesShown", 0);
    }

    public IntPrefField newInstallsPopupNumberOfTimesShown() {
        return intField("newInstallsPopupNumberOfTimesShown", 0);
    }

    public LongPrefField lastInvitePopupTime() {
        return longField("lastInvitePopupTime", 0L);
    }

    public LongPrefField lastInviteCancelPopupTime() {
        return longField("lastInviteCancelPopupTime", 0L);
    }

    public LongPrefField popupShownDayCount() {
        return longField("popupShownDayCount", 0L);
    }

    public StringPrefField inviteCampaignImageOldUrl() {
        return stringField("inviteCampaignImageOldUrl", "");
    }

    public StringPrefField inviteCampaignPopupImageOldUrl() {
        return stringField("inviteCampaignPopupImageOldUrl", "");
    }

    public StringPrefField newInstallsPopupImageOldUrl() {
        return stringField("newInstallsPopupImageOldUrl", "");
    }

    public StringPrefField inviteCampaignPopupImageSavePath() {
        return stringField("inviteCampaignPopupImageSavePath", "");
    }

    public StringPrefField inviteCampaignImageSavePath() {
        return stringField("inviteCampaignImageSavePath", "");
    }

    public StringPrefField inviteCampaignInstallsPopupImageSavePath() {
        return stringField("inviteCampaignInstallsPopupImageSavePath", "");
    }

    public BooleanPrefField inviteCampaignImageDownloaded() {
        return booleanField("inviteCampaignImageDownloaded", false);
    }

    public BooleanPrefField inviteCampaignPopupImageDownloaded() {
        return booleanField("inviteCampaignPopupImageDownloaded", false);
    }

    public BooleanPrefField inviteCampaignInstallsPopupImageDownloaded() {
        return booleanField("inviteCampaignInstallsPopupImageDownloaded", false);
    }

    public BooleanPrefField shareWithLinkOrNot() {
        return booleanField("shareWithLinkOrNot", true);
    }

    public BooleanPrefField isEventCategoryOneRequired() {
        return booleanField("isEventCategoryOneRequired", true);
    }

    public BooleanPrefField isEventCategoryTwoRequired() {
        return booleanField("isEventCategoryTwoRequired", false);
    }

    public BooleanPrefField isEventCategoryThreeRequired() {
        return booleanField("isEventCategoryThreeRequired", false);
    }

    public BooleanPrefField isForceUpdate() {
        return booleanField("isForceUpdate", false);
    }

    public StringPrefField playStoreUrl() {
        return stringField("playStoreUrl", "https://play.google.com/store/apps/details?id=com.touchtalent.bobbleapp");
    }

    public BooleanPrefField isDialogShownInThisLaunch() {
        return booleanField("isDialogShownInThisLaunch", false);
    }

    public LongPrefField lastTimeCloudSyncingDoneFromApp() {
        return longField("lastTimeCloudSyncingDoneFromApp", 0L);
    }

    public LongPrefField userPhoneNumber() {
        return longField("userPhoneNumber", 0L);
    }

    public IntPrefField userCountryCode() {
        return intField("userCountryCode", 0);
    }

    public BooleanPrefField isLoggedInForCloudSync() {
        return booleanField("isLoggedInForCloudSync", false);
    }

    public IntPrefField drawingViewHeight() {
        return intField("drawingViewHeight", 650);
    }

    public StringPrefField accessToken() {
        return stringField("accessToken", "");
    }

    public StringPrefField refreshToken() {
        return stringField("refreshToken", "");
    }

    public StringPrefField updatePopupDisplayJSONObject() {
        return stringField("updatePopupDisplayJSONObject", "null");
    }

    public StringPrefField updatePopupJSONObject1() {
        return stringField("updatePopupJSONObject1", "null");
    }

    public StringPrefField updatePopupJSONObject2() {
        return stringField("updatePopupJSONObject2", "null");
    }

    public StringPrefField updatePopupJSONObject3() {
        return stringField("updatePopupJSONObject3", "null");
    }

    public StringPrefField updatePopupJSONObject4() {
        return stringField("updatePopupJSONObject4", "null");
    }

    public StringPrefField updatePopupJSONObject5() {
        return stringField("updatePopupJSONObject5", "null");
    }

    public StringPrefField updatePopupDisplayGraphicsPath1() {
        return stringField("updatePopupDisplayGraphicsPath1", "null");
    }

    public StringPrefField updatePopupDisplayGraphicsPath2() {
        return stringField("updatePopupDisplayGraphicsPath2", "null");
    }

    public StringPrefField updatePopupDisplayGraphicsPath3() {
        return stringField("updatePopupDisplayGraphicsPath3", "null");
    }

    public StringPrefField updatePopupDisplayGraphicsPath4() {
        return stringField("updatePopupDisplayGraphicsPath4", "null");
    }

    public StringPrefField updatePopupDisplayGraphicsPath5() {
        return stringField("updatePopupDisplayGraphicsPath5", "null");
    }

    public IntPrefField updatePageCount() {
        return intField("updatePageCount", 0);
    }

    public LongPrefField generateCodeMinimumTimeStamp() {
        return longField("generateCodeMinimumTimeStamp", 0L);
    }

    public StringPrefField defaultAppInviteShareText() {
        return stringField("defaultAppInviteShareText", "bobble.in/appinvite");
    }

    public StringPrefField stickerShareWatermark() {
        return stringField("stickerShareWatermark", "");
    }

    public StringPrefField stickerShareWatermarkWithoutBobble() {
        return stringField("stickerShareWatermarkWithoutBobble", "");
    }

    public StringPrefField templateShareWatermark() {
        return stringField("templateShareWatermark", "");
    }

    public BooleanPrefField updatedWithPopup() {
        return booleanField("updatedWithPopup", false);
    }

    public BooleanPrefField hasStickerOtfEducationShown() {
        return booleanField("hasStickerOtfEducationShown", false);
    }

    public BooleanPrefField hasStickerShareEducationShown() {
        return booleanField("hasStickerShareEducationShown", false);
    }

    public BooleanPrefField hasStickerHeadEducationShown() {
        return booleanField("hasStickerHeadEducationShown", false);
    }

    public IntPrefField stickerScreenLanding() {
        return intField("stickerScreenLanding", 0);
    }

    public IntPrefField numberOfSwipesStickers() {
        return intField("numberOfSwipesStickers", 0);
    }

    public BooleanPrefField hasMyPackEducationShown() {
        return booleanField("hasMyPackEducationShown", false);
    }

    public BooleanPrefField hasTappedOnSticker() {
        return booleanField("hasTappedOnSticker", false);
    }

    public BooleanPrefField hasStoryEducationShown() {
        return booleanField("hasStoryEducationShown", false);
    }

    public BooleanPrefField hasHeadEducationShown() {
        return booleanField("hasHeadEducationShown", false);
    }

    public BooleanPrefField isReturningOnBobbleSelection() {
        return booleanField("isReturningOnBobbleSelection", false);
    }

    public BooleanPrefField hasDownloadedOnePack() {
        return booleanField("hasDownloadedOnePack", false);
    }

    public BooleanPrefField autoUpdatePackOnWifi() {
        return booleanField("autoUpdatePackOnWifi", false);
    }

    public BooleanPrefField autoUpdatePackOnCellular() {
        return booleanField("autoUpdatePackOnCellular", false);
    }

    public LongPrefField latestDownloadedPackId() {
        return longField("latestDownloadedPackId", 176L);
    }

    public StringPrefField referralStickerPackId() {
        return stringField("referralStickerPackId", "");
    }

    public BooleanPrefField hasUserActionTakenOnWidget() {
        return booleanField("hasUserActionTakenOnWidget", false);
    }

    public BooleanPrefField userFromUpgrade() {
        return booleanField("userFromUpgrade", false);
    }

    public BooleanPrefField useClientAuthentication() {
        return booleanField("useClientAuthentication", false);
    }

    public BooleanPrefField isCameraEducationLaunch() {
        return booleanField("isCameraEducationLaunch", false);
    }

    public BooleanPrefField isEraserEducationLaunch() {
        return booleanField("isEraserEducationLaunch", false);
    }

    public BooleanPrefField isLanguagePrefUpdated() {
        return booleanField("isLanguagePrefUpdated", false);
    }

    public StringPrefField countryCodeForAPIs() {
        return stringField("countryCodeForAPIs", "");
    }

    public BooleanPrefField isBobbleDeleteEducationLaunch() {
        return booleanField("isBobbleDeleteEducationLaunch", false);
    }

    public StringPrefField genderOfFirstHead() {
        return stringField("genderOfFirstHead", "");
    }

    public StringPrefField genderOfSecondHead() {
        return stringField("genderOfSecondHead", "");
    }

    public StringPrefField countryCodeFromServer() {
        return stringField("countryCodeFromServer", "");
    }

    public BooleanPrefField packDeletionOnCountryCodeExecuted() {
        return booleanField("packDeletionOnCountryCodeExecuted", false);
    }

    public BooleanPrefField refreshMascotList() {
        return booleanField("refreshMascotList", false);
    }

    public StringPrefField selectedHeadType() {
        return stringField("selectedHeadType", "personal");
    }

    public BooleanPrefField isBubbleEducationShown() {
        return booleanField("isBubbleEducationShown", false);
    }

    public StringPrefField seededStickerPackIds() {
        return stringField("seededStickerPackIds", "[\"1\",\"87\",\"59\",\"57\",\"86\",\"31\",\"79\"]");
    }

    public BooleanPrefField isAssetsUrlParsingCompleteOnce() {
        return booleanField("isAssetsUrlParsingCompleteOnce", false);
    }

    public StringPrefField recentEmoticons() {
        return stringField("recentEmoticons", "");
    }

    public BooleanPrefField emojiEducationShown() {
        return booleanField("emojiEducationShown", false);
    }

    public StringPrefField enabledLanguagesKeyboard() {
        return stringField("enabledLanguagesKeyboard", "[\"english\"]");
    }

    public StringPrefField wordsAddedFromSettings() {
        return stringField("wordsAddedFromSettings", "");
    }

    public BooleanPrefField stateMachineHasTappedOnSticker() {
        return booleanField("stateMachineHasTappedOnSticker", false);
    }

    public BooleanPrefField stateMachineHasSharedASticker() {
        return booleanField("stateMachineHasSharedASticker", false);
    }

    public BooleanPrefField stateMachineHasMadeOtfSticker() {
        return booleanField("stateMachineHasMadeOtfSticker", false);
    }

    public BooleanPrefField stateMachineHasVisitedStickerStore() {
        return booleanField("stateMachineHasVisitedStickerStore", false);
    }

    public BooleanPrefField stateMachineHasVisitedStorySection() {
        return booleanField("stateMachineHasVisitedStorySection", false);
    }


    public BooleanPrefField stateMachineHasEnabledKeyboard() {
        return booleanField("stateMachineHasEnabledKeyboard", false);
    }

    public BooleanPrefField stateMachineHasTappedOnGif() {
        return booleanField("stateMachineHasTappedOnGif", false);
    }

    public BooleanPrefField stateMachineHasSharedAGif() {
        return booleanField("stateMachineHasSharedAGif", false);
    }

    public StringPrefField stateMachineConfig() {
        return stringField("stateMachineConfig", "{\"minHour\":9,\"maxHour\":23,\"coolingPeriod\":1,\"gifSingleTap\":{\"shortMessage\":\"Tap & share a GIF instantly\",\"longMessage\":\" Impress your friends with cool animated stickers on your whatsapp chats.\",\"showNotification\":true},gifShare:{\"shortMessage\":\"Want to amaze your friends?\",\"longMessage\":\"Instantly share hilarious GIFs with your friends on whatsapp!\",\"showNotification\":true},\"stickerSingleTap\":{\"shortMessage\":\"Tap on any sticker to share\",\"longMessage\":\"You're looking cool in your bobble stickers. Tap on any sticker to share with your friends.\",\"showNotification\":true},stickerShare:{\"shortMessage\":\"Be a star! Show off your new swag\",\"longMessage\":\"Be a star! Share a witty one-liner in your friends' Whatsapp group.\",\"showNotification\":true},enableKeyboard:{\"shortMessage\":\"Share stickers from the keyboard directly!\",\"longMessage\":\"Did you know that you can share stickers directly from your keyboard?\",\"showNotification\":true},stickerOTF:{\"shortMessage\":\"Edit text on any sticker\",\"longMessage\":\"Edit sticker with your message in your language and share with friend’s Whatsapp group\",\"showNotification\":true},storyVisit:{\"shortMessage\":\"Check out your comic stories\",\"longMessage\":\"We've made hilarious comics with your face, edit your dialogues and share with friends.\",\"showNotification\":true},storeVisit:{\"shortMessage\": \"Check out NEW sticker packs\",\"longMessage\":\"New stickers are available for you to share on WhatsApp!\",\"showNotification\":true}}");
    }

    public BooleanPrefField isFallbackToTypeOneOriginal() {
        return booleanField("isFallbackToTypeOneOriginal", false);
    }

    public BooleanPrefField isUseBobbleTypeEight() {
        return booleanField("isUseBobbleTypeEight", true);
    }

    public IntPrefField keyboardStickerSharesNormal() {
        return intField("keyboardStickerSharesNormal", 0);
    }

    public IntPrefField keyboardStickerSharesOtf() {
        return intField("keyboardStickerSharesOtf", 0);
    }

    public IntPrefField keyboardEmojisUsedTopRow() {
        return intField("keyboardEmojisUsedTopRow", 0);
    }

    public IntPrefField keyboardFontsChanged() {
        return intField("keyboardFontsChanged", 0);
    }

    public BooleanPrefField hasEnableKeyboardEducationShown() {
        return booleanField("hasEnableKeyboardEducationShown", false);
    }


    public BooleanPrefField hasAutoKeyboardRatingShown() {
        return booleanField("hasAutoKeyboardRatingShown", false);
    }

    public BooleanPrefField uploadAppDebugData() {
        return booleanField("uploadAppDebugData", false);
    }

    public IntPrefField daysUntilKeyboardRatingIsShown() {
        return intField("daysUntilKeyboardRatingIsShown", 0);
    }

    public LongPrefField keyboardRatingLastShownOn() {
        return longField("keyboardRatingLastShownOn", 0);
    }

    public BooleanPrefField hasAssetsUrlCallCompleted() {
        return booleanField("hasAssetsUrlCallCompleted", false);
    }

    public StringPrefField keyboardSelectedFont() {
        return stringField("keyboardSelectedFont", "Basic");
    }

    public BooleanPrefField enableAutoExpression() {
        return booleanField("enableAutoExpression", true);
    }

    public StringPrefField disableStickerExpressionsV2() {
        return stringField("disableStickerExpressionsV2", "");
    }

    public StringPrefField disableStickerWigs() {
        return stringField("disableStickerWigs", "");
    }

    public StringPrefField disableStickerAccessories() {
        return stringField("disableStickerAccessories", "");
    }

    /*public StringPrefField useStickerExpressionsV2() {
        return stringField("useStickerExpressionsV2", BobbleConstants.YES);
    }

    public StringPrefField useStickerWigs() {
        return stringField("useStickerWigs", BobbleConstants.YES);
    }*/

    public BooleanPrefField isMissingExpressionAndWigDataReceived() {
        return booleanField("isMissingExpressionAndWigDataReceived", false);
    }

    public StringPrefField recentStoreSearches() {
        return stringField("recentStoreSearches", "");
    }

    public IntPrefField emojiKeywordMappingCurrentVersion() {
        return intField("emojiKeywordMappingCurrentVersion", 1);
    }

    public BooleanPrefField isEmojiMappingUpdated() {
        return booleanField("isEmojiMappingUpdated", true);
    }

    public BooleanPrefField hasSetAlarmForEnabledKeyboard() {
        return booleanField("hasSetAlarmForEnabledKeyboard", false);
    }

    public BooleanPrefField mKeyboardShownOnce() {
        return booleanField("mKeyboardShownOnce", false);
    }

    public StringPrefField fontList() {
        return stringField("fontList", "");
    }

    public StringPrefField fontSelectedCount() {
        return stringField("fontSelectedCount", "");
    }


    public IntPrefField stickerSuggestionCurrentVersion() {
        return intField("stickerSuggestionCurrentVersion", 0);
    }

    public BooleanPrefField disableStickerSuggestions() {
        return booleanField("disableStickerSuggestions", true);
    }

    public LongPrefField lastTimeConfigCalledSuccessFully() {
        return longField("lastTimeConfigCalledSuccessFully", 0L);
    }

    public BooleanPrefField keyboardEnablePopUpShownOnce() {
        return booleanField("keyboardEnablePopUpShownOnce", false);
    }

    public StringPrefField abTests() {
        return stringField("abTests", "{}");
    }

    public BooleanPrefField eraserEducationShown() {
        return booleanField("eraserEducationShown", false);
    }

    public BooleanPrefField hairColorEducationShown() {
        return booleanField("hairColorEducationShown", false);
    }

    public BooleanPrefField faceCleanerEducationShown() {
        return booleanField("faceCleanerEducationShown", false);
    }

    public BooleanPrefField isKeyBorderEnabled() {
        return booleanField("isKeyBorderEnabled", false);
    }

    public BooleanPrefField isAccentedCharacterEnabled() {
        return booleanField("isAccentedCharacter", true);
    }

    public IntPrefField currentThemeId() {
        return intField("currentThemeId", 0);
    }

    public IntPrefField keyboardOpenCount() {
        return intField("keyboardOpenCount", 0);
    }

    public StringPrefField autoDownloadStickerCategories() {
        return stringField("autoDownloadStickerCategories", "");
    }

    public BooleanPrefField isRedDotOnSeededStickerShown() {
        return booleanField("isRedDotOnSeededStickerShown", false);
    }

    public StringPrefField bobbleAnimationPackAutoDownloadList() {
        return stringField("bobbleAnimationPackAutoDownloadList", "");
    }

    public StringPrefField recentGifs() {
        return stringField("recentGifs", "");
    }

    public StringPrefField newGifPacks() {
        return stringField("newGifPacks", "");
    }

    public StringPrefField referralBobbleAnimationPackId() {
        return stringField("referralBobbleAnimationPackId", "");
    }

    public StringPrefField stickerPackViewedEventSentFor() {
        return stringField("stickerPackViewedEventSentFor", "");
    }

    public StringPrefField gifPackViewedEventSentFor() {
        return stringField("gifPackViewedEventSentFor", "");
    }

    public StringPrefField keyboardLanguages() {
        return stringField("keyboardLanguages", "[{\"languageId\":1,\"languageCode\":\"en\",\"languageNativeName\":\"English\",\"languageName\":\"English\",\"currentVersion\":2,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_1_589d563c9fb5c.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.15},{\"languageId\":3,\"languageCode\":\"hi\",\"languageNativeName\":\"\\u0939\\u093f\\u0928\\u094d\\u0926\\u0940\",\"languageName\":\"Hindi\",\"currentVersion\":4,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_3_589e0ea5e4b3a.zip\",\"isValidDictionary\":true,\"enableTransliteration\":true,\"autoCorrectThreshold\":0.185},{\"languageId\":2,\"languageCode\":\"gu\",\"languageNativeName\":\"\\u0a97\\u0ac1\\u0a9c\\u0ab0\\u0abe\\u0aa4\\u0ac0\",\"languageName\":\"Gujarati\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_2.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":4,\"languageCode\":\"kn\",\"languageNativeName\":\"\\u0c95\\u0ca8\\u0ccd\\u0ca8\\u0ca1\",\"languageName\":\"Kannada\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_4.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":5,\"languageCode\":\"pa\",\"languageNativeName\":\"\\u0a2a\\u0a70\\u0a1c\\u0a3e\\u0a2c\\u0a40\",\"languageName\":\"Punjabi\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_5.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":6,\"languageCode\":\"ta\",\"languageNativeName\":\"\\u0ba4\\u0bae\\u0bbf\\u0bb4\\u0bcd\",\"languageName\":\"Tamil\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_6.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":7,\"languageCode\":\"te\",\"languageNativeName\":\"\\u0c24\\u0c46\\u0c32\\u0c41\\u0c17\\u0c41\",\"languageName\":\"Telugu\",\"currentVersion\":2,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_7_589d5654591fd.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":8,\"languageCode\":\"bn\",\"languageNativeName\":\"\\u09ac\\u09be\\u0982\\u09b2\\u09be\",\"languageName\":\"Bengali\",\"currentVersion\":4,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_8_58b1590239fa0.zip\",\"isValidDictionary\":true,\"enableTransliteration\":true,\"autoCorrectThreshold\":0.185},{\"languageId\":9,\"languageCode\":\"ml\",\"languageNativeName\":\"\\u0d2e\\u0d32\\u0d2f\\u0d3e\\u0d33\\u0d02\",\"languageName\":\"Malayalam\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_9_586365a034ed5.zip\",\"isValidDictionary\":true,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":20,\"languageCode\":\"or\",\"languageNativeName\":\"\\u0b13\\u0b21\\u0b3c\\u0b3f\\u0b06 \\u0b2d\\u0b3e\\u0b37\\u0b3e \",\"languageName\":\"Odia\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_20_58932dbfdf0b2.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":25,\"languageCode\":\"raj\",\"languageNativeName\":\"\\u0930\\u093e\\u091c\\u0938\\u094d\\u0925\\u093e\\u0928\\u0940\",\"languageName\":\"Rajasthani\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_25_589d5f3030fd1.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":22,\"languageCode\":\"mwr\",\"languageNativeName\":\"\\u092e\\u093e\\u0930\\u0935\\u093e\\u0921\\u093c\\u0940\",\"languageName\":\"Marwari\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_22_589d5ee320d92.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":21,\"languageCode\":\"bho\",\"languageNativeName\":\"\\u092d\\u094b\\u091c\\u092a\\u0941\\u0930\\u0940\",\"languageName\":\"Bhojpuri\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_21_589d5ec8e19ba.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":24,\"languageCode\":\"sn\",\"languageNativeName\":\"\\u0938\\u093f\\u0928\\u094d\\u0927\\u0940\",\"languageName\":\"Sindhi\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_24_589d5f160416c.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":23,\"languageCode\":\"kok\",\"languageNativeName\":\"\\u0915\\u094b\\u0902\\u0915\\u0923\\u0940\",\"languageName\":\"Konkani\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_23_589d5efc5348c.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":11,\"languageCode\":\"as\",\"languageNativeName\":\"\\u0985\\u09b8\\u09ae\\u09c0\\u09af\\u09bc\\u09be\",\"languageName\":\"Assamese\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_11_58932b96bf5a9.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":16,\"languageCode\":\"mai\",\"languageNativeName\":\"\\u092e\\u0948\\u0925\\u093f\\u0932\\u0940\",\"languageName\":\"Maithili\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_16_58932c2f4c12d.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":18,\"languageCode\":\"mni\",\"languageNativeName\":\"\\u09ae\\u09c8\\u09a4\\u09c8\\u09b2\\u09cb\\u09a8\\u09cd\",\"languageName\":\"Manipuri\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_18_58932c7978225.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":19,\"languageCode\":\"sat\",\"languageNativeName\":\"\\u0938\\u0902\\u0924\\u093e\\u0932\\u0940\",\"languageName\":\"Santali\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_19_58932c9fe2a32.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":26,\"languageCode\":\"doi\",\"languageNativeName\":\"\\u0921\\u094b\\u0917\\u0930\\u0940\",\"languageName\":\"Dogri\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_26_589d6b1f6e294.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":15,\"languageCode\":\"ne\",\"languageNativeName\":\"\\u0928\\u0947\\u092a\\u093e\\u0932\\u0940\",\"languageName\":\"Nepali\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_15_58932c1c0ac98.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185},{\"languageId\":27,\"languageCode\":\"mr\",\"languageNativeName\":\"\\u092e\\u0930\\u093e\\u0920\\u0940\",\"languageName\":\"Marathi\",\"currentVersion\":1,\"resourceUrl\":\"http:\\/\\/assets.bobbleapp.me\\/keyboard_language_resources\\/bobble_keyboard_language_resources_27_58eb366441f96.zip\",\"isValidDictionary\":false,\"enableTransliteration\":false,\"autoCorrectThreshold\":0.185}]");
    }

    public IntPrefField scheduledJobId() {
        return intField("scheduledJobId", -1);
    }

    public IntPrefField scheduledBobbleBinaryDictSyncJobId() {
        return intField("scheduledBobbleBinaryDictSyncJobId", -1);
    }

    public BooleanPrefField canShowNewLanguagesIcon() {
        return booleanField("canShowNewLanguagesIcon", true);
    }

    public StringPrefField lastSelectedLanguage() {
        return stringField("lastSelectedLanguage", "");
    }

    public BooleanPrefField keyboardCloudSyncSettingsRetrieved() {
        return booleanField("keyboardCloudSyncSettingsRetrieved", false);
    }

    public StringPrefField internationalizationLanguage() {
        return stringField("internationalizationLanguage", "");
    }

    public StringPrefField internationalizationLanguageSelected() {
        return stringField("internationalizationLanguageSelected", "");
    }

    public BooleanPrefField canShowInternationalizationPrompt() {
        return booleanField("canShowInternationalizationPrompt", false);
    }

    public BooleanPrefField isGifTabSelected() {
        return booleanField("isGifTabSelected", false);
    }

    public BooleanPrefField disableGuggyIntegration() {
        return booleanField("disableGuggyIntegration", false);
    }

    public StringPrefField systemLanguageInternationalization() {
        return stringField("systemLanguageInternationalization", "");
    }

    public StringPrefField toastShownForSystemLanguageInternationalization() {
        return stringField("toastShownForSystemLanguageInternationalization", "");
    }

    private StringPrefField fontAnimationPackList() {
        return stringField("fontAnimationPackList", "");
    }

    public LongPrefField lastTimeCacheCleared() {
        return longField("lastTimeCacheCleared", 0L);
    }

    public StringPrefField defaultStickerShareText() {
        return stringField("defaultStickerShareText", "MakeMyBobble.in/stickers");
    }

    public StringPrefField defaultComicShareText() {
        return stringField("defaultComicShareText", "MakeMyBobble.in/comics");
    }

    public StringPrefField defaultAnimationShareText() {
        return stringField("defaultAnimationShareText", "MakeMyBobble.in/gif");
    }

    public StringPrefField referralStickerShareText() {
        return stringField("referralStickerShareText", "");
    }

    public StringPrefField referralComicShareText() {
        return stringField("referralComicShareText", "");
    }

    public StringPrefField referralAnimationShareText() {
        return stringField("referralAnimationShareText", "");
    }

    public BooleanPrefField disableShareViaAccessibility() {
        return booleanField("disableShareViaAccessibility", false);
    }

    public StringPrefField shareViaAccessibilityApps() {
        return stringField("shareViaAccessibilityApps", "");
    }

    public BooleanPrefField isShareViaAccessibilityAppsDataUpdated() {
        return booleanField("isShareViaAccessibilityAppsDataUpdated", false);
    }

    public BooleanPrefField isWhatsHandlerWorking() {
        return booleanField("isWhatsHandlerWorking", true);
    }

    public IntPrefField whatsHandlerFailSession() {
        return intField("whatsHandlerFailSession", 1);
    }

    public IntPrefField accessibilitySessionCount() {
        return intField("accessibilitySessionCount", 0);
    }

    public StringPrefField guggyResultList() {
        return stringField("guggyResultList", "");
    }

    public StringPrefField guggyResultForOtfList() {
        return stringField("guggyResultForOtfList", "");
    }

    public IntPrefField guggySessionCount() {
        return intField("guggySessionCount", 1);
    }

    public BooleanPrefField userSawGuggyGif() {
        return booleanField("userSawGuggyGif", false);
    }

    public StringPrefField guggyGifUniqueIdentifier() {
        return stringField("guggyGifUniqueIdentifier", "212");
    }

    public StringPrefField guggyOtfGifUniqueIdentifier() {
        return stringField("guggyOtfGifUniqueIdentifier", "121");
    }

    public StringPrefField guggyAndBobbleDisplayOrder() {
        return stringField("guggyAndBobbleDisplayOrder", "{gifSuggestionsOrder:[\"bobble_normal_gif\",\"bobble_font_gif\",\"guggy_gif\"]}");
    }

    public StringPrefField accessibilitySupportedApp() {
        return stringField("accessibilitySupportedApp", "[\"com.whatsapp\"]");
    }

    public IntPrefField personalisedFrequencyThreshold() {
        return intField("personalisedFrequencyThreshold", 2);
    }

    public IntPrefField currentLanguageBinaryDictVersion(String languageCode) {
        return intField("currentLanguageBinaryDictVersion_" + languageCode, 0);
    }

    public LongPrefField lastBobbleBinaryDictSyncTime() {
        return longField("lastBobbleBinaryDictSyncTime", 0L);
    }

    public IntPrefField guggyPackPosition() {
        return intField("guggyPackPosition", -1);
    }

    public StringPrefField stickerPackBannerViewedEvent() {
        return stringField("stickerPackBannerViewedEvent", "[]");
    }

    public StringPrefField storyViewedEvent() {
        return stringField("stickerPackBannerViewedEvent", "[]");
    }

    public StringPrefField referralTemplateId() {
        return stringField("referralTemplateId", "");
    }

    public BooleanPrefField hasSeenFacebookShareViaKeyboardEdu() {
        return booleanField("hasSeenFacebookShareViaKeyboardEdu", false);
    }

    public IntPrefField oneTapSharingEducationSessionCount() {
        return intField("oneTapSharingEducationSessionCount", 1);
    }

    public IntPrefField oneTapSharingEducationSessionCountInApp() {
        return intField("oneTapSharingEducationSessionCountInApp", 1);
    }

    public StringPrefField animationShareWatermark() {
        return stringField("animationShareWatermark", "");
    }

    public StringPrefField customThemes() {
        return stringField("customThemes", "[]");
    }

    public IntPrefField bobbleGifDefaultLockingTime() {
        return intField("bobbleGifDefaultLockingTime", 43200);
    }

    public LongPrefField gifDownloadStartTime() {
        return longField("gifDownloadStartTime", 0l);
    }

    public BooleanPrefField showAccessibilityRedDotEducation() {
        return booleanField("showAccessibilityRedDotEducation", false);
    }

    public BooleanPrefField isCustomThemeCreated() {
        return booleanField("isCustomThemeCreated", false);
    }

    private StringPrefField userDeletedGifPacksList() {
        return stringField("userDeletedGifPacksList", "");
    }

    public BooleanPrefField upgradeLogicForStickerShowInTabRan() {
        return booleanField("upgradeLogicForStickerShowInTabRan", false);
    }

    public StringPrefField contentTranslationLanguage() {
        return stringField("contentTranslationLanguage", "[]");
    }

    public StringPrefField gifCatalogPackViewedEvent() {
        return stringField("gifCatalogPackViewedEvent", "[]");
    }

    public StringPrefField geoLocationCountryCode() {
        return stringField("geoLocationCountryCode", "");
    }

    public IntPrefField directSharingContactListMaxParsingTime() {
        return intField("directSharingContactListMaxParsingTime", 6);
    }

    public StringPrefField geoLocationAdmin1() {
        return stringField("geoLocationAdmin1", "");
    }

    public StringPrefField geoLocationAdmin2() {
        return stringField("geoLocationAdmin2", "");
    }

    public BooleanPrefField disableUserWordListUpload() {
        return booleanField("disableUserWordListUpload", true);
    }

    public BooleanPrefField seedingFinished() {
        return booleanField("seedingFinished", false);
    }

    public BooleanPrefField disableSimilarWebSDK() {
        return booleanField("disableSimilarWebSDK", true);
    }

    public BooleanPrefField disableDirectSharingAccessibilityPermissionPopup() {
        return booleanField("disableDirectSharingAccessibilityPermissionPopup", false);
    }


    /*
     * Below are the prefs which are attached with GSON converter factory
     */
    private static final Gson GSON = new GsonBuilder().serializeNulls().create();

   /* public ABTests getABTests() {
        return GSON.fromJson(abTests().get(), ABTests.class);
    }

    public String getABTestsAsString(ABTests abTests) {
        return GSON.toJson(abTests);
    }

    public void setABTests(ABTests abTests) {
        abTests().put(GSON.toJson(abTests));
    }*/

    public List<String> getBobbleAnimationFontsList() {
        String fontAnimationPackList = fontAnimationPackList().get();
        Gson gson = new Gson();
        if (fontAnimationPackList != null && !fontAnimationPackList.isEmpty()) {
            try {
                return gson.fromJson(fontAnimationPackList, new TypeToken<List<String>>() {
                }.getType());
            } catch (JsonSyntaxException jEx) {
                //no need to do anything here, the last return statement work.
            }
        }
        return new ArrayList<>();
    }

    public void setBobbleAnimationFontsList(Long fontAnimationPackId) {
        Gson gson = new Gson();
        if (fontAnimationPackList().get().isEmpty()) {
            List<String> bobbleFontAnimationPackIds = new ArrayList<>();
            bobbleFontAnimationPackIds.add(String.valueOf(fontAnimationPackId));
            String fontAnimations = gson.toJson(bobbleFontAnimationPackIds);
            fontAnimationPackList().put(fontAnimations);
        } else {
            List<String> bobbleFontAnimationPackIds = gson.fromJson(fontAnimationPackList().get(), new TypeToken<List<String>>() {
            }.getType());
            bobbleFontAnimationPackIds.add(String.valueOf(fontAnimationPackId));
            String fontAnimations = gson.toJson(bobbleFontAnimationPackIds);
            fontAnimationPackList().put(fontAnimations);
        }
    }

    public List<Long> getUserDeletedGifPacksList() {
        String userDeletedGifPacks = userDeletedGifPacksList().get();
        Gson gson = new Gson();
        if (!TextUtils.isEmpty(userDeletedGifPacks)) {
            try {
                return gson.fromJson(userDeletedGifPacks, new TypeToken<List<Long>>() {
                }.getType());
            } catch (JsonSyntaxException ignored) {

            }
        }

        return new ArrayList<>();
    }

    public void addUpdateUserDeletedGifPacksList(Long animationPackId) {
        Gson gson = new Gson();
        if (userDeletedGifPacksList().get().isEmpty()) {
            List<String> userDeletedPackIds = new ArrayList<>();
            userDeletedPackIds.add(String.valueOf(animationPackId));
            userDeletedGifPacksList().put(gson.toJson(userDeletedPackIds));
        } else {
            List<String> userDeletedPackIds = gson.fromJson(userDeletedGifPacksList().get(), new TypeToken<List<String>>() {
            }.getType());

            if (!userDeletedPackIds.contains(String.valueOf(animationPackId))) {
                userDeletedPackIds.add(String.valueOf(animationPackId));
            }
            userDeletedGifPacksList().put(gson.toJson(userDeletedPackIds));
        }
    }

    public BooleanPrefField isContentLanguageSwitchedToSysLanguageEventSent() {
        return booleanField("isContentLanguageSwitchedToSysLanguageEventSent", false);
    }
}
