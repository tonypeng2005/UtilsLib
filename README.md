# UtilsLib

UtilsLib contains some useful utilities, see below.

##Usage
-----------
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency to your app build.gradle
```
	dependencies {
	        compile 'com.github.tonypeng2005:UtilsLib:0.0.3'
	}
```
-----------
## ReviewDialog
-----------
Simply add one line in MainActivity

```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReviewDialog.show(this, 5); // Review Dialog will show after 5 times launch
    }
}
```
![alt text](https://github.com/tonypeng2005/UtilsLib/blob/master/screenshot/ReviewDialog_screenshot.png)
-----------
