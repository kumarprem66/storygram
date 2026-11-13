# StoryGram public pages

Static pages for the StoryGram Android app, published with GitHub Pages at
https://kumarprem66.github.io/storygram/

| Page | URL | Used by |
|---|---|---|
| Privacy policy | `/privacy` | Play Console (Policy → Privacy policy), app Settings → Privacy Policy |
| Terms of service | `/terms` | app Settings → Terms of Service |
| Help & support | `/help` | app Settings → Help |
| Delete your account | `/delete-account` | Play Console (Data safety → account deletion URL) |

**This repository is the only copy** — edit the `.html` files here, commit and push to `main`;
GitHub Pages republishes within a minute or two. The app finds these pages through
`storyverse.webBaseUrl` in `storyverse-android/gradle.properties`.

Keep the support email (`storygram.support@gmail.com`) the same in every page, and keep the
privacy policy in line with the Data safety answers in Play Console whenever the app starts
collecting something new.
