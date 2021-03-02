# Android-vivy-coding-challange

# Android Coding Task
Please implement the stories below. The code will be used as a basis of discussion on the next step of the interview with the current Android developers.
User Stories - Vivy Doctor List
As a user I want to see
● a list of doctors.</br>
● last 3 recently selected doctors on top of my doctors list.</br>
● more information whenever I select any doctor.</br>
Endpoint - Doctors:</br>
https://vivy.com/interviews/challenges/android/doctors.json </br>
To fetch the next page you need to take the "lastKey" in the response and add it in the url path so it will become ​doctors-​{insert last key here}​.json</br>
e.g ​https://vivy.com/interviews/challenges/android/doctors-lastkey.json</br>
Acceptance criteria:</br>
● As I scroll down the doctor list , more results should be added until there are no more results left.</br>
● The list of doctors should have two sections</br>
○ List displaying recently selected doctors sorted by most recent. This section</br>
should have heading "Recent Doctors"</br>
○ List displaying Vivy doctors (followed by doctor list excluding recently</br>
selected doctor) sorted by highest rating. This section should have heading</br>
"Vivy Doctors".</br>
● Detailed doctor screen should display name, address and picture of selected doctor.</br>
● Code should be covered by tests.</br>
● Use Stable android studio version to do this challenge. Please don’t use release candidates or canary builds.</br>
● Please use Kotlin.</br>
Some tips:</br>
● Only vertical screen orientation is required.</br>
● There is no need to cache the doctor list locally which is fetched by the api.</br>
● Don't spend too much time on UI. Keep it bare minimum.</br>

● Project should be developed with production quality. Our technical team isn’t just checking the end result, they will be evaluating code challenge based on code quality, architecture and problem solving skills.</br>
Bonus Task:</br>
● Add search bar that filter search results by doctor name as user types name of the</br>
doctor. (no endpoint required for this)</br>
