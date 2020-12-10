# Dividers for RecyclerView
## Attention
There is an official implementation which is [DividerItemDecoration](https://developer.android.com/reference/androidx/recyclerview/widget/DividerItemDecoration). You should use this one.

## Intention
This is just a simple demo app to demonstrate how to draw dividers for RecyclerView. 

RecyclerView doesn't provide a straightforward interface for drawing dividers of lists. But actually it provides us a much more flexible
way to draw dividers. We use [RecyclerView.ItemDecoration](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ItemDecoration.html)
to decorate RecyclerView's tiles with dividers or anything you want. That is also why it is called ItemDecoration.

## Implementions
There are three different implemetations of dividers.
- [UnderneathDivider](https://github.com/gejiaheng/Dividers-For-RecyclerView/blob/master/app/src/main/java/com/ge/demo/divider/decorator/UnderneathDivider.java)
- [OverlayDivider](https://github.com/gejiaheng/Dividers-For-RecyclerView/blob/master/app/src/main/java/com/ge/demo/divider/decorator/OverlayDivider.java)
- [InsetDivider](https://github.com/gejiaheng/Dividers-For-RecyclerView/blob/master/app/src/main/java/com/ge/demo/divider/decorator/InsetDivider.java)

As described in [Meterial Design Guidelines](https://material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B_udO5B8pzrzYi1pc290WFRMc1U/components_dividers_specs.png):

> The divider sits within the baseline of the tile.

So I would suggest using `OverlayDivider` or `InsetDivider`. `OverlayDivider` draws dividers on the tiles, needing no extra inset. `InsetDivider` can draw dividers on or underneath the tiles. 

![](https://material-design.storage.googleapis.com/publish/material_v_8/material_ext_publish/0B_udO5B8pzrzYi1pc290WFRMc1U/components_dividers_specs.png)

## Usage
- UnderneathDivider  
```java
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.addItemDecoration(new UnderneathDivider(this, UnderneathDivider.VERTICAL_LIST));
```  
- OverlayDivider
```java
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.addItemDecoration(new OverlayDivider(this, OverlayDivider.VERTICAL_LIST));
```
- InsetDivider
```java
ItemDecoration divider = new InsetDivider.Builder(this)
                            .orientation(InsetDivider.VERTICAL_LIST) 
                            .dividerHeight(getResources().getDimensionPixelSize(R.dimen.divider_height))
                            .color(getResources().getColor(R.color.colorAccent))
                            .insets(getResources().getDimensionPixelSize(R.dimen.divider_inset), 0)
                            .overlay(true)
                            .build(); 
                            
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.addItemDecoration(divider);
```

## License
   Copyright 2016 gejiaheng

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
