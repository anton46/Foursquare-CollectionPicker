# Forsquare-CollectionPicker
Collection Picker is an Android View library which looks like Foursquare Tastes picker. For more information see the [website](https://github.com/anton46/Forsquare-CollectionPicker).

![Example image](./image1.png)    ![Example image](./image2.png)



## Usage

**Gradle**
```gradle
compile 'com.anton46:collection-picker:1.0.0'
````
**Maven**
```xml
<dependency>
    <groupId>com.anton46</groupId>
    <artifactId>collection-picker</artifactId>
    <version>1.0.0</version>
</dependency>
````

Declare a `CollectionPicker` inside your XML layout file 

```xml
 <com.anton46.collectionitempicker.CollectionPicker
        android:id="@+id/collection_item_picker"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:cp_addIcon="@android:drawable/ic_menu_add"
        app:cp_cancelIcon="@android:drawable/ic_menu_close_clear_cancel"
        app:cp_itemBackgroundNormal="@color/blue"
        app:cp_itemBackgroundPressed="@color/red"
        app:cp_itemMargin="5dp"
        app:cp_textPaddingBottom="5dp"
        app:cp_textPaddingLeft="10dp"
        app:cp_textPaddingRight="10dp"
        app:cp_textPaddingTop="5dp" />
```

Add Items
```java
        List<Item> items = new ArrayList<>();
        items.add(new Item("item1", "Items 1"));
        
        CollectionPicker picker = (CollectionPicker) findViewById(R.id.collection_item_picker);
        picker.setItems(items);
        picker.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(Item item, int position) {
            
            }
        });
```

Configure using xml attributes in code : 

```xml
        //Layout radius
        <attr name="cp_itemRadius" format="dimension" />
        //Layout background normal
        <attr name="cp_itemBackgroundNormal" format="color" />
        //Layout background pressed/selected
        <attr name="cp_itemBackgroundPressed" format="color" />
        //Add icon indicator
        <attr name="cp_addIcon" format="reference" />
        //Cancel icon indicator
        <attr name="cp_cancelIcon" format="reference" />
        //Margin beetwen items (Horizontal and vertical)
        <attr name="cp_itemMargin" format="dimension" />
        //Text color
        <attr name="cp_itemTextColor" format="color"/>
        //Text paddding to left
        <attr name="cp_textPaddingLeft" format="dimension" />
         //Text paddding to right 
        <attr name="cp_textPaddingRight" format="dimension" />
         //Text paddding to top 
        <attr name="cp_textPaddingTop" format="dimension" />
         //Text paddding to bottom  
        <attr name="cp_textPaddingBottom" format="dimension" />
```

Developed by
========================
* Anton Nurdin Tuhadiansyah (anton.work19@gmail.com)
* [anton46.com][1]

[1]: http://anton46.com

License
-----------

```
Copyright 2015 Anton Nurdin Tuhadiansyah

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


