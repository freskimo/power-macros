# PowerMacros
PowerMacros is plug-in for BurpSuite proxy. It provides extended functionality of BurpSuite's built-in macros feature. This extension allows you to pipe automated requests (when certain conditions are met) to JavaScript files for pre-processing before being sent outbound. With JavaScript files you load, you can manipulate Burp Suite's automated requests on the fly. PowerMacros integrates powerfully with Burp Suite's features, in particular its target scoping functionality. 

## Examples of Practical Uses
Incorporating script files allows you There are many ways this tool can be used. These are just a few examples of PowerMacros capabilities:
- CSRF token replacement
- Session token replacement.
- Tailoring an XSS string to the automated request.

## Features
- Adding new headers
- Modifying existing headers
- Extracting data from arbitrary response
- Modification of automated outbound request
  
## Advantages over BurpSuite's built-in macros
- Able to perform string modification in automated request arbitrary strings in the request. 
- ability to add new header into the request

## About the UI
The plug-in adds new tab into the BurpSuite named "PowerMacros". Within the "PowerMacros" tab are two tabs: "Extract" and "Replace".


#### Extract Tab
Here you can set up what requests will be triggered and what data will be extracted from their responses. After selecting the message, you can set the extraction by the selection of the response.

#### Replace Tab
Here you can set up what data from extraction will be added/replaced in the following requests. The replace string can be set by the selection of the request.

### Settings
He you can specifies what tool will use the PowerMacros plug-in.

## How to
1. Select messages e,g, in the Proxy tab, do right mouse click and select "Send to PowerMacros"
2. go to PowerMacros and click on the message in the "Extraction message list"
3. select data from the response editor
4. click "From selection" button
5. set extraction name and click "Add" button
6. click on the message in the "Replace message list"
7. select data from the request editor and click "From selection" button
8. set replace name and type (replace on the selected message)
9. select the extraction
10. click replaces "Add" button
11. now you are done and your request will be triggered, you can see it in the "Logger" tab.

## Screen-shots

Advanced URL Scoping Functionality. When the "in scope" URL conditions are met, user-specified string manipulation of outgoing requests will be triggered.

![Main tab](/screenshots/0_session_handling_rule_3_advanced.png?raw=true "Main tab")

Main: Example Extractions

![Logger tab](/screenshots/0_main_extract_example.png?raw=true "Logger tab")

Main: Example Replacements

![Settings tab](/screenshots/0_main_replace_example.png?raw=true "Settings tab")