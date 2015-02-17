# Forsquare-CollectionPicker
Collection Picker is an Android View library which looks like Forsquare Taste picker

![Example image](./image1.png)    ![Example image](./image2.png)



## Usage
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
* [1]: anton46.com

