# StoryVerse API Documentation

> Base URL: `http://localhost:8080`  
> Authentication: Bearer JWT token in `Authorization` header  
> Content-Type: `application/json`

---

## Execution Order

Run APIs in this order to test the full flow:

1. Register → 2. Verify Email → 3. Login → 4. Become Creator → 5. Create Story →
6. Create Chapter → 7. Update Story → 8. Update Chapter → 9. Publish Story →
10. Start Reading → 11. Update Progress → 12. Add Bookmark → 13. Create Collection →
14. Add Comment → 15. Rate Story → 16. Follow User → 17. Search Stories →
18. Get Recommendations → 19. Get Notifications → 20. Subscribe

---

## 1. Identity Module (`/api/v1/auth`, `/api/v1/users`)

### 1.1 Register User

```
POST /api/v1/auth/register
```

**Body:**
```json
{
  "email": "writer@example.com",
  "username": "writer01",
  "password": "MyPassword123!",
  "displayName": "Creative Writer"
}
```

**Response (201):**
```json
{
  "success": true,
  "data": {
    "userId": "2e2946e1-e4b8-42bf-8bd0-3419bfe2b8d5",
    "message": "Registration successful. Please verify your email."
  }
}
```

**Notes:**
- Check application console logs for the verification token
- User status: `PENDING_VERIFICATION`, role: `READER`
- Email must be unique, username must be 3-30 chars (alphanumeric + underscore)

---

### 1.2 Verify Email

```
POST /api/v1/auth/verify-email?token={token-from-logs}
```

**Response (200):**
```json
{
  "success": true,
  "data": null,
  "message": "Email verified successfully"
}
```

**Notes:**
- Token is printed in console: `Generated email verification token for user <id> (token: <uuid>)`
- Changes status from `PENDING_VERIFICATION` → `ACTIVE`
- Without this step, login will fail

---

### 1.3 Login

```
POST /api/v1/auth/login
```

**Body:**
```json
{
  "email": "writer@example.com",
  "password": "MyPassword123!"
}
```

**Response (200):**
```json
{
  "success": true,
  "data": {
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "a1b2c3d4-e5f6-..."
  }
}
```

**Notes:**
- Use `accessToken` as `Authorization: Bearer <token>` for all subsequent requests
- Access token expires in 15 minutes (configurable)
- 5 failed login attempts → account auto-locks

---

### 1.4 Refresh Token

```
POST /api/v1/auth/refresh
```

**Body:**
```json
{
  "refreshToken": "a1b2c3d4-e5f6-..."
}
```

**Response (200):** Same as login response (new token pair, old refresh token revoked)

---

### 1.5 Logout

```
POST /api/v1/auth/logout
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "refreshToken": "a1b2c3d4-e5f6-..."
}
```

**Response (200):**
```json
{ "success": true, "data": null }
```

---

### 1.6 Change Password

```
PUT /api/v1/users/me/password
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "currentPassword": "MyPassword123!",
  "newPassword": "NewPassword456!"
}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Password changed successfully" }
```

**Notes:** All existing tokens are revoked after password change (forces re-login)

---

## 2. Creator Module (`/api/v1/creators`)

### 2.1 Become Creator

```
POST /api/v1/creators
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "displayName": "Creative Writer"
}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "creatorId": "f1a2b3c4-..." }
}
```

**Notes:**
- A user can only have one creator profile
- Calling again returns `409 CREATOR_EXISTS`

---

### 2.2 Get Creator Profile

```
GET /api/v1/creators/{creatorId}
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "success": true,
  "data": {
    "id": "f1a2b3c4-...",
    "userId": "2e2946e1-...",
    "displayName": "Creative Writer",
    "biography": null,
    "avatarUrl": null,
    "bannerUrl": null,
    "website": null,
    "genres": [],
    "penNames": [],
    "language": null,
    "status": "ACTIVE",
    "verificationStatus": "UNVERIFIED",
    "followerCount": 0,
    "storyCount": 0,
    "createdAt": "2026-08-07T10:00:00Z",
    "updatedAt": "2026-08-07T10:00:00Z"
  }
}
```

---

### 2.3 Update Creator Profile

```
PUT /api/v1/creators/{creatorId}
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "displayName": "Pro Writer",
  "biography": "I write fantasy and sci-fi stories",
  "website": "https://mywebsite.com",
  "language": "en",
  "genres": ["FANTASY", "SCIENCE_FICTION"],
  "penNames": ["The Wordsmith"]
}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Creator profile updated" }
```

---

## 3. Story Module (`/api/v1/stories`)

### 3.1 Create Story

```
POST /api/v1/stories
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "title": "The Dragon's Legacy",
  "description": "An epic fantasy about a young mage discovering ancient dragon magic",
  "genre": "FANTASY",
  "language": "en"
}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "storyId": "72d1caed-bdec-4fdf-89ff-5ebf2c5c1119" }
}
```

**Notes:**
- Story is created with status `DRAFT` and visibility `PRIVATE`
- Genre values: FANTASY, SCIENCE_FICTION, ROMANCE, MYSTERY, THRILLER, HORROR, ADVENTURE, HISTORICAL, LITERARY_FICTION, YOUNG_ADULT, CHILDREN, NON_FICTION, POETRY, DRAMA, COMEDY, OTHER

---

### 3.2 Get Story

```
GET /api/v1/stories/{storyId}
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "success": true,
  "data": {
    "id": "72d1caed-...",
    "authorId": "2e2946e1-...",
    "title": "The Dragon's Legacy",
    "subtitle": null,
    "description": "An epic fantasy...",
    "genre": "FANTASY",
    "tags": [],
    "language": "en",
    "ageRating": "EVERYONE",
    "visibility": "PRIVATE",
    "status": "DRAFT",
    "coverImageUrl": null,
    "wordCount": 0,
    "estimatedReadingTimeMinutes": 1,
    "publishedAt": null,
    "createdAt": "2026-08-07T10:05:00Z",
    "updatedAt": "2026-08-07T10:05:00Z"
  }
}
```

---

### 3.3 Update Story Metadata

```
PUT /api/v1/stories/{storyId}
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "title": "The Dragon's Legacy: Awakening",
  "subtitle": "Book One of the Mage Chronicles",
  "description": "An epic fantasy about a young mage discovering ancient dragon magic in a world on the brink of war",
  "genre": "FANTASY",
  "tags": ["dragons", "magic", "epic", "coming-of-age"],
  "language": "en",
  "ageRating": "TEEN"
}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Story updated successfully" }
```

**Notes:**
- Only the story author can update
- ageRating values: EVERYONE, TEEN, MATURE, ADULT

---

### 3.4 Create Chapter

```
POST /api/v1/stories/{storyId}/chapters
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "title": "Chapter 1: The Awakening",
  "content": "The morning sun pierced through the ancient forest canopy, casting golden rays upon the sleeping village of Thornhaven. Young Elara stirred in her bed, unaware that this day would change everything she knew about her world and herself."
}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "chapterId": "a1b2c3d4-..." }
}
```

**Notes:**
- Chapters are auto-ordered (first chapter = index 0, second = 1, etc.)
- Story word count is automatically recalculated
- Only the story owner can add chapters

---

### 3.5 Update Chapter

```
PUT /api/v1/stories/{storyId}/chapters/{chapterId}
Authorization: Bearer {accessToken}
```

**Body:**
```json
{
  "title": "Chapter 1: The Awakening (Revised)",
  "content": "Dawn broke over Thornhaven with an unusual warmth. The ancient oaks surrounding the village seemed to lean inward, as if listening for something. Young Elara opened her eyes to find her fingertips glowing with a faint blue light."
}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Chapter updated successfully" }
```

---

### 3.6 Delete Chapter

```
DELETE /api/v1/stories/{storyId}/chapters/{chapterId}
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{ "success": true, "data": null }
```

---

### 3.7 Publish Story

```
POST /api/v1/stories/{storyId}/publish
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Story published successfully" }
```

**Prerequisites:**
- Story must have a title (non-blank)
- Story must have a description (non-blank)
- Story must have at least 1 chapter
- Only the author can publish

**What changes:**
- status: `DRAFT` → `PUBLISHED`
- visibility: `PRIVATE` → `PUBLIC`
- publishedAt: set to current timestamp

**Error cases:**
- No chapters → `422 STORY_NO_CHAPTERS`
- No title → `422 STORY_TITLE_REQUIRED`
- No description → `422 STORY_DESCRIPTION_REQUIRED`
- Not owner → `403 ACCESS_DENIED`

---

### 3.8 Archive Story

```
POST /api/v1/stories/{storyId}/archive
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Story archived successfully" }
```

**Notes:** Sets status to `ARCHIVED`, visibility to `PRIVATE`

---

## 4. Reader Module (`/api/v1/reading`, `/api/v1/bookmarks`, `/api/v1/collections`)

### 4.1 Start Reading

```
POST /api/v1/reading/start?storyId={storyId}&chapterId={chapterId}
Authorization: Bearer {accessToken}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "progressId": "b2c3d4e5-..." }
}
```

**Notes:**
- Idempotent: calling again for same story returns existing progressId
- Creates a ReadingProgress with status `IN_PROGRESS`

---

### 4.2 Update Reading Progress

```
PUT /api/v1/reading/progress?storyId={storyId}&chapterId={chapterId}&chapterIndex=1&percentage=65.5&sessionSeconds=420
Authorization: Bearer {accessToken}
```

**Parameters:**
- `storyId` — the story being read
- `chapterId` — current chapter
- `chapterIndex` — zero-based chapter position
- `percentage` — overall progress (0.0 to 100.0)
- `sessionSeconds` — reading time in this session (added to total)

**Response (200):**
```json
{ "success": true, "data": null, "message": "Progress updated" }
```

---

### 4.3 Add Bookmark

```
POST /api/v1/bookmarks?storyId={storyId}&chapterId={chapterId}&note=Great%20chapter
Authorization: Bearer {accessToken}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "bookmarkId": "c3d4e5f6-..." }
}
```

---

### 4.4 Create Collection

```
POST /api/v1/collections?name=My%20Favorites&description=Best%20stories&visibility=PRIVATE
Authorization: Bearer {accessToken}
```

**Parameters:**
- `name` — collection name (required)
- `description` — optional
- `visibility` — `PUBLIC` or `PRIVATE` (default: PRIVATE)

**Response (201):**
```json
{
  "success": true,
  "data": { "collectionId": "d4e5f6a7-..." }
}
```

---

## 5. Community Module (`/api/v1/stories/{storyId}/comments`, `/api/v1/follows`, `/api/v1/stories/{storyId}/ratings`)

### 5.1 Add Comment

```
POST /api/v1/stories/{storyId}/comments?content=Amazing%20story!
Authorization: Bearer {accessToken}
```

**Parameters:**
- `content` — comment text (required)
- `parentCommentId` — optional, for replies

**Response (201):**
```json
{
  "success": true,
  "data": { "commentId": "e5f6a7b8-..." }
}
```

---

### 5.2 Reply to Comment

```
POST /api/v1/stories/{storyId}/comments?parentCommentId={commentId}&content=I%20agree!
Authorization: Bearer {accessToken}
```

---

### 5.3 Rate Story

```
POST /api/v1/stories/{storyId}/ratings?score=5
Authorization: Bearer {accessToken}
```

**Parameters:**
- `score` — 1 to 5 (required)

**Response (200):**
```json
{ "success": true, "data": null, "message": "Rating saved" }
```

**Notes:**
- One rating per user per story
- Calling again updates the existing rating (upsert)

---

### 5.4 Follow User

```
POST /api/v1/follows/{userId}
Authorization: Bearer {accessToken}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "followId": "f6a7b8c9-..." }
}
```

**Error cases:**
- Self-follow → `409 SELF_FOLLOW`
- Already following → `409 ALREADY_FOLLOWING`

---

## 6. Search Module (`/api/v1/search`)

### 6.1 Search Stories

```
GET /api/v1/search/stories?keyword=dragon&genre=FANTASY&language=en&sortBy=RELEVANCE&page=0&size=10
Authorization: Bearer {accessToken}
```

**Parameters (all optional):**
- `keyword` — search text
- `genre` — filter by genre
- `language` — filter by language code
- `ageRating` — filter by age rating
- `tags` — filter by tags (comma-separated)
- `sortBy` — RELEVANCE, NEWEST, OLDEST, MOST_READ, HIGHEST_RATED, TRENDING
- `page` — page number (default 0)
- `size` — page size (default 20, max 100)

**Response (200):**
```json
{
  "success": true,
  "data": []
}
```

**Notes:** Returns empty in dev (in-memory index needs stories to be indexed via IndexStoryUseCase)

---

### 6.2 Autocomplete

```
GET /api/v1/search/autocomplete?prefix=dra&limit=5
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "success": true,
  "data": ["Dragon's Legacy", "Dragonborn"]
}
```

---

## 7. Recommendation Module (`/api/v1/recommendations`)

### 7.1 Get Recommendations

```
GET /api/v1/recommendations?type=PERSONALIZED&limit=10
Authorization: Bearer {accessToken}
```

**Parameters:**
- `type` — PERSONALIZED, TRENDING, NEW_RELEASES (default: PERSONALIZED)
- `limit` — max items (default: 10)

**Response (200):**
```json
{
  "success": true,
  "data": []
}
```

**Notes:** Returns empty in dev (stub engine). Production uses AI-assisted ranking.

---

### 7.2 Get Similar Stories

```
GET /api/v1/recommendations/similar/{storyId}?limit=5
Authorization: Bearer {accessToken}
```

---

### 7.3 Submit Recommendation Feedback

```
POST /api/v1/recommendations/feedback?storyId={storyId}&feedbackType=NOT_INTERESTED
Authorization: Bearer {accessToken}
```

**feedbackType values:** NOT_INTERESTED, HIDE_STORY

---

## 8. Notification Module (`/api/v1/notifications`)

### 8.1 Get My Notifications

```
GET /api/v1/notifications
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "success": true,
  "data": []
}
```

**Notes:** Notifications are created by the system when events occur (story published, new follower, etc.)

---

### 8.2 Mark Notification as Read

```
PUT /api/v1/notifications/{notificationId}/read
Authorization: Bearer {accessToken}
```

---

## 9. AI Platform Module (`/api/v1/ai`)

### 9.1 Continue Writing

```
POST /api/v1/ai/writer/continue?context=The%20hero%20stood%20at%20the%20edge%20of%20the%20cliff
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "success": true,
  "data": { "result": "[AI Stub Response for CONTINUE_WRITING]..." }
}
```

---

### 9.2 Rewrite Text

```
POST /api/v1/ai/writer/rewrite?text=The%20man%20walked%20slow%20to%20store
Authorization: Bearer {accessToken}
```

---

### 9.3 Summarize Text

```
POST /api/v1/ai/writer/summarize?text=Long%20story%20text%20here...
Authorization: Bearer {accessToken}
```

---

### 9.4 Generate Title

```
POST /api/v1/ai/writer/generate-title?description=A%20story%20about%20a%20young%20wizard
Authorization: Bearer {accessToken}
```

---

### 9.5 Summarize Story (Reader)

```
POST /api/v1/ai/reader/summarize-story?content=Once%20upon%20a%20time...
Authorization: Bearer {accessToken}
```

---

### 9.6 Explain Text (Reader)

```
POST /api/v1/ai/reader/explain?text=The%20protagonist%20experienced%20an%20epiphany
Authorization: Bearer {accessToken}
```

**Notes:** All AI endpoints return stub responses in dev. Production connects to OpenAI/Gemini/Anthropic.

---

## 10. Analytics Module (`/api/v1/analytics`)

### 10.1 Get Platform Dashboard

```
GET /api/v1/analytics/dashboard
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{
  "success": true,
  "data": {
    "totalUsers": 0,
    "totalStories": 0,
    "totalReads": 0,
    "totalComments": 0,
    "activeUsersToday": 0,
    "storiesPublishedToday": 0,
    "aiRequestsToday": 0
  }
}
```

---

### 10.2 Get Metric Trends

```
GET /api/v1/analytics/trends?metricType=STORY_READ&timeWindow=DAILY&from=2026-07-01&to=2026-08-07
Authorization: Bearer {accessToken}
```

**metricType values:** STORY_VIEW, STORY_READ, STORY_COMPLETED, CHAPTER_COMPLETED, USER_REGISTERED, COMMENT_CREATED, REACTION_ADDED, FOLLOW_CREATED, STORY_PUBLISHED, STORY_RATED, SEARCH_EXECUTED, AI_REQUEST, NOTIFICATION_SENT, RECOMMENDATION_SERVED

**timeWindow values:** HOURLY, DAILY, WEEKLY, MONTHLY, YEARLY

---

## 11. Billing Module (`/api/v1/billing`)

### 11.1 Subscribe

```
POST /api/v1/billing/subscribe?plan=PREMIUM
Authorization: Bearer {accessToken}
```

**plan values:** FREE, PREMIUM, PREMIUM_PLUS

**Response (201):**
```json
{
  "success": true,
  "data": { "subscriptionId": "a7b8c9d0-..." }
}
```

**Error cases:**
- Already subscribed → `409 ACTIVE_SUBSCRIPTION_EXISTS`

---

### 11.2 Cancel Subscription

```
POST /api/v1/billing/cancel
Authorization: Bearer {accessToken}
```

**Response (200):**
```json
{ "success": true, "data": null, "message": "Subscription cancelled" }
```

---

## 12. Moderation Module (`/api/v1/moderation`)

### 12.1 Report Content

```
POST /api/v1/moderation/report?targetId={storyId}&targetType=STORY&reason=SPAM&description=Looks%20like%20spam
Authorization: Bearer {accessToken}
```

**Parameters:**
- `targetId` — UUID of the reported content
- `targetType` — STORY, COMMENT, REVIEW, USER
- `reason` — SPAM, HARASSMENT, HATE_SPEECH, ADULT_CONTENT, VIOLENCE, COPYRIGHT, MISINFORMATION, IMPERSONATION, OTHER
- `description` — optional details

**Response (201):**
```json
{
  "success": true,
  "data": { "caseId": "b8c9d0e1-..." }
}
```

---

### 12.2 Resolve Moderation Case (Admin/Moderator)

```
POST /api/v1/moderation/cases/{caseId}/resolve?action=WARNING&notes=First%20offense
Authorization: Bearer {accessToken}
```

**action values:** WARNING, CONTENT_REMOVAL, CONTENT_HIDING, TEMPORARY_SUSPENSION, PERMANENT_BAN, NONE

---

## 13. Administration Module (`/api/v1/admin`)

### 13.1 List Feature Flags

```
GET /api/v1/admin/feature-flags
Authorization: Bearer {accessToken}
```

---

### 13.2 Create Feature Flag

```
POST /api/v1/admin/feature-flags?name=ai_writing&description=Enable%20AI%20writing%20features
Authorization: Bearer {accessToken}
```

**Response (201):**
```json
{
  "success": true,
  "data": { "flagId": "c9d0e1f2-..." }
}
```

---

### 13.3 Enable Feature Flag

```
POST /api/v1/admin/feature-flags/{flagId}/enable
Authorization: Bearer {accessToken}
```

---

### 13.4 Disable Feature Flag

```
POST /api/v1/admin/feature-flags/{flagId}/disable
Authorization: Bearer {accessToken}
```

---

### 13.5 Get Config Value

```
GET /api/v1/admin/config/{key}
Authorization: Bearer {accessToken}
```

---

### 13.6 Set Config Value

```
PUT /api/v1/admin/config/{key}?value=100&description=Max%20stories%20per%20user
Authorization: Bearer {accessToken}
```

---

## Error Response Format

All errors follow this structure:

```json
{
  "success": false,
  "errorCode": "RESOURCE_NOT_FOUND",
  "message": "Story not found with ID: 72d1caed-...",
  "path": "/api/v1/stories/72d1caed-...",
  "fieldErrors": null,
  "metadata": {
    "timestamp": "2026-08-07T10:30:00Z",
    "correlationId": null
  }
}
```

**HTTP Status Mapping:**
| Code | Meaning |
|------|---------|
| 400 | Validation failed (field errors included) |
| 401 | Not authenticated / invalid credentials |
| 403 | Not authorized (e.g., not the owner) |
| 404 | Resource not found |
| 409 | Conflict (duplicate, already exists) |
| 422 | Business rule violation |
| 500 | Internal server error |
