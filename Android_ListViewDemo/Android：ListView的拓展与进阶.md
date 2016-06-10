##写在前面

很长时间没有更新博客了，因为最近一直在写即时聊天的Demo。跟着教程完成了一个基于XMPP的IM,完成后又写了一个基于环信的IM。感觉真心学了很多东西，都想分享出来，依次来吧。首先想说的就是关于ListView的一些知识。

ListView是Android中**最常见也最难用的控件**，因为ListView用处很多，几乎所有的APP都会用到这样一个控件。它基本的用法大家都知道，这里我就不再赘述了。本文主要是ListView的一些扩展知识，有以下几个方面：

* 更改自带ListView主题的样式
* 多样式ListView
* ViewHolder的两种写法
* ListView的效率优化
* 一些干货网站推荐

##更改自带ListView主题的样式

我们知道，在大部分APP开发环境中，ListView都是需要自定义的。这里说的自定义意思是需要我们自定义一个适配器继承基本的Adapter类，比如BaseAdapter等。因为基本的Adapter不满足一些复杂的列表效果展示。当我们自定义Adapter的时候，就可以复写父类的方法，从而实现复杂的列表。比如这样：

![比较复杂的ListView](http://www.iamxiarui.com/wp-content/uploads/2016/05/比较复杂的ListView.png)

而小部分场景呢，我们只需要基本的Adapter就可以了，比如只需要展示字符串，这个时候只需要绑定ArrayAdapter就能满足需求。

![简单的ListView](http://www.iamxiarui.com/wp-content/uploads/2016/05/简单ListView.png)

下面就是上面这个ListView的具体代码，很简单，不再解释。

    /**
     * @ClassName: MainActivity
     * @Description:ListView的拓展
     * @author: iamxiarui@foxmail.com
     * @date: 2016年5月30日 下午2:15:46
     */
    public class MainActivity extends Activity {
    
        private ListView mainListView;
        private ArrayList<String> listData;
    
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
    
            initView();
    
            initData();
    
            initAdapter();
        }
    
        /**
         * @Title: initView
         * @Description:初始化View
         * @return: void
         */
        private void initView() {
            mainListView = (ListView) findViewById(R.id.lv_main);
        }
    
        /**
         * @Title: initData
         * @Description:初始化列表数据
         * @return: void
         */
        private void initData() {
            listData = new ArrayList<String>();
            for (int i = 0; i < 30; i++) {
                listData.add("这是第 " + i + " 条数据");
            }
        }
    
        /**
         * @Title: initAdapter
         * @Description:绑定适配器
         * @return: void
         */
        private void initAdapter() {
            mainListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData));
        }
    
    }

可以看出，这里ListView的样式是android自带的样式**android.R.layout.simple-list-item-1**。当然说的重点不是这个，现在我们设想有这样一个需求。我们只需要展示字符串，没有复杂的样式，不需要自定义Adapter,但是我们需要在android自带样式的基础上，将列表中文字的颜色改成其他颜色或者更换文字大小。那该怎么做呢？

没错！就是复写**getView()**方法。代码如下：

    /**
     * @Title: initAdapter
     * @Description:绑定适配器，并更改自带主题样式
     * @return: void
     */
    private void initAdapter() {
        mainListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // 这里我们知道，item中只有一个TextView，可以直接写
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextColor(Color.BLUE);
                textView.setTextSize(36);
                return textView;
            }
        });
    }

现在运行后可以看出，样式已经发生了改变。

![更改样式后的ListView](http://www.iamxiarui.com/wp-content/uploads/2016/05/更改样式后的ListView.png)

所以在没有自定义Adapter的前提下，我们仍然能对基本的Adapter做一些样式上的更改。

##多样式ListView

多样式ListView也是比较常见的，意思就是一个ListView中的item有多种样式。最常见的莫过于聊天记录的ListView了，发送者与接收者的消息是不同的样式。

![聊天记录ListView](http://www.iamxiarui.com/wp-content/uploads/2016/05/聊天记录ListView.png)

那这样是如何做的呢？毫无疑问，我们需要自定义Adapter，但是在这里，我们复写的方法除了常用的**getCount()、getItem()、getItemId()、getView()**四个方法外，我们还需要复写下面两个方法：

* public int getViewTypeCount() ：这个方法是指定ListView中样式的数目，默认返回1

* public int getItemViewType(int position) ：根据参数positon指定当前item的样式类型

现在我们试着写出如下效果的ListView，条目依次不同，相当于一个ListView中存在两种样式。

![两种样式的ListView](http://www.iamxiarui.com/wp-content/uploads/2016/05/两种样式的ListView.png)

代码中的重点就是下面的自定义Adapter,重点中的重点就是上面说到的两个方法的复写，具体如下：

    /**
     * @ClassName: MyListAdapter
     * @Description:自定义两种样式的适配器
     * @author: iamxiarui@foxmail.com
     * @date: 2016年5月30日 下午2:51:00
     */
    public class MyListAdapter extends BaseAdapter {
    
        private Context context;
        private ArrayList<String> list;
    
        public MyListAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }
    
        @Override
        public int getCount() {
            return list.size();
        }
    
        @Override
        public Object getItem(int position) {
    
            return list.get(position);
        }
    
        @Override
        public long getItemId(int position) {
    
            return position;
        }
    
        /**
         * @Title: getViewTypeCount
         * @Description:两种样式
         * @return: 样式的数目
         */
        @Override
        public int getViewTypeCount() {
            return 2;
        }
    
        /**
         * @Title: getItemViewType
         * @Description:每个item的样式
         * @param position：item位置
         * @return: 样式
         */
        @Override
        public int getItemViewType(int position) {
            // 为了方便起见，我们根据奇偶项来区别样式，注意从0开始数
            if (position % 2 != 0) {
                // 奇数项返回0
                return 0;
            } else {
                // 偶数项返回0
                return 1;
            }
        }
    
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.item_list, null);
            }
            view = convertView;
            // 如果是奇数项
            if (getItemViewType(position) == 0) {
                TextView itemText = (TextView) view.findViewById(R.id.tv_item);
                itemText.setText(list.get(position));
                itemText.setTextColor(Color.BLUE);
            }
            // 如果是偶数项
            else if (getItemViewType(position) == 1) {
                TextView itemText = (TextView) view.findViewById(R.id.tv_item);
                itemText.setText(list.get(position));
                itemText.setTextColor(Color.GRAY);
            }
            return view;
        }
    
    }

这样我们就完成了ListView中存在多样式的实现，有了这个简单例子，当然可以实现聊天记录列表或者其他更加复杂的多样式。

##ViewHolder的两种写法

ViewHolder是官方提出的一种优化ListView的工具，主要是减少了一些重复的操作，极大的提升了ListView的一些性能，当然这个大家也都非常熟悉了。

用我们之前的例子来看，它的一般写法是这样的：

    /**
     * @ClassName: ViewHolder
     * @Description:定义ViewHolder类
     * @author: iamxiarui@foxmail.com
     * @date: 2016年5月30日 下午3:28:05
     */
    private static class ViewHolder {
        TextView itemText;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // 不存在的时候，创建ViewHolder
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_list, null);

            viewHolder.itemText = (TextView) convertView.findViewById(R.id.tv_item);

            viewHolder.itemText.setText(list.get(position));

            // 存储ViewHolder
            convertView.setTag(viewHolder);
        } else {
            // 存在的话直接复用
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

效果图跟第一幅图一样，这里就不贴了。这种写法也是谷歌官方的写法，一般来说大家都这样写。

但是在看Github上一个开源库的时候，看到一个大神是这样写的：

    /**
     * @ClassName: ViewHolder
     * @Description:定义ViewHolder类
     * @author: iamxiarui@foxmail.com
     * @date: 2016年5月30日 下午3:28:05
     */
    private static class ViewHolder {
        TextView itemText;

        // 构造函数中就初始化View
        public ViewHolder(View convertView) {
            itemText = (TextView) convertView.findViewById(R.id.tv_item);
        }

        // 得到一个ViewHolder
        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            return viewHolder;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list, null);
        }
        // 得到一个ViewHolder
        viewHolder = ViewHolder.getViewHolder(convertView);
        viewHolder.itemText.setText(list.get(position));

        return convertView;
    }

对比来看，在定义ViewHolder类的时候，需要实现构造函数，并封装得到ViewHolder的方法，看似是比原来的复杂很多，但是仔细推敲，这样做的好处有以下几点：

* 将ViewHolder的实现方法完全封装在其本身的类中，功能更强大
* 其方法与Adapter的getView()方法隔离，条理更加清晰
* 当getView()中需要实现多种样式时，不需要写重复代码

所以在开发过程中，我一般选取第二种方式，虽然看似复杂很多，但是更加符合逻辑。

##ListView的效率优化

关于ListView的优化是很多开发者感到困扰的问题，比如ListView的卡顿、图片加载异常等问题十分棘手。其实提高ListView的流畅度只要记着两点就行了:

* 复用View中的item
* 异步处理耗时任务

这个问题在《Android开发艺术探索》中有专门的介绍，具体大概有四种方法：

###1、使用ViewHoler

不论是getView()方法中的**convertView**还是使用ViewHolder，其实基本原理都是复用已经用过的item。也就是在滑动过程中不需要一直new出新的View。而关于ViewHolder的具体使用，上面已经说过了，这里就不在赘述了。

###2、不在getView()中执行耗时任务

其实这个跟网络请求要在子线程中执行一样，不在UI线程中执行耗时任务，这样会造成主线程异常的卡顿。同样的在ListView中也不能在getView()方法中执行耗时任务，比如从网络上请求一些图片之类的。

关于加载图片我们可以使用ImageLoader实现，也就是异步的方式处理。

###3、控制异步任务的执行频率

其实这句话的意思就是，滑动的时候不执行异步加载，等停止滑动的时候再执行异步任务。这样就避免了滑动过程中的卡顿问题。

具体实现方式可以在**onScrollStateChange()**方法中定义一个滑动标志，然后在getView()中判断这个标志，如果已经停止滑动，那就启动加载任务。

###4、开启硬件加速

一般来说，复用View与异步加载方式已经能够解决ListView卡顿的情况，如果你还是不满意的话，可以尝试开启硬件加速，也可以解决一些卡顿问题。开启方式是在**AndroidManifest.xml**中对应的Activity添加下面这句：

> **android:hardwareAccelerated = "true"**

##干货分享

* 优秀网站:

[AndroidCat——安卓书签网](http://androidcat.com)

是专门为Android开发者而收集整理的网站资源导航。有很多优秀的资源、开发工具等。

* 开源库：

[大量优秀的Android开源组件库](https://github.com/XXApple/AndroidLibs)

由于水平有限，难免会有错误，欢迎指正与交流。
