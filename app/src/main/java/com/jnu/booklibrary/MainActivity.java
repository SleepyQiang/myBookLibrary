package com.jnu.booklibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.jnu.booklibrary.data.Book;
import com.jnu.booklibrary.data.DataSaver;
import com.jnu.booklibrary.dialog.MyDialog;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private CardView myID;
    private Toolbar myToolbar;
    private ActionBarDrawerToggle myToggle;
    private MyAdapter myMyAdapter;
    private DrawerLayout myDrawerLayout;
    private LinearLayout myLinearLayout;
    private TextView myDrawerText;
    private FloatingActionButton myFab;
    private Spinner mySpinner;
    private SearchView mySearchView;
    private ListView myListView;
    private ArrayList<String> mySpinnerList;
    private ArrayAdapter<String> mySpinnerAdapter;
    private String[] myStrs = {"家", "春", "秋"};

    ArrayList<Book> myBookList = new ArrayList<>();
    public static final int MENU_ID_ADD = 1;
    public static final int MENU_ID_UPDATE = 2;
    public static final int MENU_ID_DELETE = 3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        return true;
    }

    private ActivityResultLauncher<Intent> addDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

        if (null != result) {
            Intent intent = result.getData();

            if (result.getResultCode() == InputBookListActivity.RESULT_CODE) {
                Bundle bundle = intent.getExtras();

                String name = bundle.getString("name");
                String author = bundle.getString("author");
                String rank = bundle.getString("rank");
                String year = bundle.getString("year");
                String press = bundle.getString("press");
                String isbn = bundle.getString("isbn");
                int position = bundle.getInt("position");

                myBookList.add(position, new Book(name, R.drawable.wa, author, press, year, rank, isbn));
                new DataSaver().Save(this, myBookList);
                myMyAdapter.notifyItemInserted(position);
            }
        }
    });

    private ActivityResultLauncher<Intent> updateDataLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

        if (null != result) {
            Intent intent = result.getData();

            if (result.getResultCode() == InputBookListActivity.RESULT_CODE) {
                Bundle bundle = intent.getExtras();

                String name = bundle.getString("name");
                String author = bundle.getString("author");
                String rank = bundle.getString("rank");
                String year = bundle.getString("year");
                String press = bundle.getString("press");
                String isbn = bundle.getString("isbn");
                int position = bundle.getInt("position");

                myBookList.get(position).setTitle(name);
                myBookList.get(position).setAuthor(author);
                myBookList.get(position).setRank(rank);
                myBookList.get(position).setYear(year);
                myBookList.get(position).setPress(press);
                myBookList.get(position).setIsbn(isbn);
                new DataSaver().Save(this, myBookList);
                myMyAdapter.notifyItemChanged(position);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myID = findViewById(R.id.CardView);

        myID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                myDrawerLayout.openDrawer(myLinearLayout);
            }
        });

        myRecyclerView = this.findViewById(R.id.recyclerview);

//        实现左上角菜单图标打开drawer，存在bug
//        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        myToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);

//        myToggle = new ActionBarDrawerToggle(this, myDrawerLayout, myToolbar, R.string.welcome_to_drawer, R.string.sure_to_delete);
//        myToggle.syncState();

        myDrawerLayout = findViewById(R.id.drawer_main);
        myDrawerText = findViewById(R.id.drawer_text);
        myLinearLayout = findViewById(R.id.drawer_left);

        myDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

                Toast.makeText(MainActivity.this, "抽屉菜单打开", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

                Toast.makeText(MainActivity.this, "抽屉菜单关闭", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mySearchView = findViewById(R.id.SearchView);
        myListView = findViewById(R.id.ListView);
        myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStrs));
        myListView.setTextFilterEnabled(true);

        // 设置搜索文本监听
        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {  // 此方法的作用是对搜索框里的文字实时监听。

                if (!TextUtils.isEmpty(newText)) {
                    myListView.setFilterText(newText);
                } else {
                    myListView.clearTextFilter();
                }

                return false;
            }
        });

        Book b0 = new Book("家", R.drawable.jia, "巴金", "人民文学出版社", "2013-6-1", "8.3", "9787020096466");
        Book b1 = new Book("春", R.drawable.chun, "巴金", "人民文学出版社", "2013-6-1", "8.3", "9787020096473");
        Book b2 = new Book("秋", R.drawable.qiu, "巴金", "人民文学出版社", "2013-6-1", "8.5", "9787020096480");

        mySpinner = findViewById(R.id.spinner);
        mySpinnerList = new ArrayList<String>();
        mySpinnerAdapter = new ArrayAdapter<String>(this, R.layout.item_for_custom_spinner, mySpinnerList);

        mySpinnerList.add("全部");
        mySpinnerList.add("书架一号");
        mySpinnerList.add("书架二号");

        mySpinner.setAdapter(mySpinnerAdapter);

        final int[] flag = {0};
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (flag[0] == 0) {
                    flag[0] += 1;
                    return;
                }

                switch (i) {
                    case 0:
                    case 1:
                    case 2:
                        Toast.makeText(MainActivity.this, "切换到 " + mySpinnerAdapter.getItem(i), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        DataSaver dataSaver = new DataSaver();
        myBookList = dataSaver.Load(this);

        if (myBookList.size() == 0) {

            myBookList.add(b0);
            myBookList.add(b1);
            myBookList.add(b2);
        }

        myMyAdapter = new MyAdapter();
        myRecyclerView.setAdapter(myMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager); // RecycleView implementation

        myFab = findViewById(R.id.floatingActionButton);
        myFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, InputBookListActivity.class);
                intent.putExtra("position", 0);

                addDataLauncher.launch(intent);
            }
        });
    }

    public List<Book> getListBooks() {

        return myBookList;
    }

    public void showBookInfo(Book book) {

        MyDialog myDialog = new MyDialog(MainActivity.this, book);

        myDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(MainActivity.this, "头像！", Toast.LENGTH_SHORT).show();
            case R.id.app_bar_search:
                Toast.makeText(MainActivity.this, "要" + item.toString() + "些什么呢？", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_collection:
                Toast.makeText(MainActivity.this, "查看" + item.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_fontsize:
                Toast.makeText(MainActivity.this, "调整 " + item.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_share:
                Toast.makeText(MainActivity.this, "要" + item.toString() + "给谁？", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, InputBookListActivity.class);
                intent.putExtra("position", item.getOrder());

                addDataLauncher.launch(intent);
                break;
            case 2:
                Intent intentUpdate = new Intent(this, InputBookListActivity.class);
                intentUpdate.putExtra("position", item.getOrder());
                intentUpdate.putExtra("name", myBookList.get(item.getOrder()).getTitle());

                updateDataLauncher.launch(intentUpdate);
                break;
            case 3:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                myBookList.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this, myBookList);
                                myMyAdapter.notifyItemRemoved(item.getOrder());
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).create();

                alertDialog.show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = View.inflate(MainActivity.this, R.layout.book_list, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Book book = myBookList.get(position);
            holder.myImageView.setImageDrawable(getResources().getDrawable(book.getCoverResourceId()));
            holder.myTextRank.setText(book.getRank());
            holder.myTextView.setText(book.getTitle());
            holder.myTextAuthor.setText(book.getAuthor());
            holder.myTextYear.setText(book.getYear());
            holder.myCardView.setOnClickListener(view -> showBookInfo(book));
        }

        @Override
        public int getItemCount() {

            return myBookList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        ImageView myImageView;
        TextView myTextRank;
        TextView myTextView;
        TextView myTextAuthor;
        TextView myTextYear;
        CardView myCardView;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            myImageView = itemView.findViewById(R.id.imageView);
            myTextView = itemView.findViewById(R.id.textView);
            myCardView = itemView.findViewById(R.id.cardView);
            myTextRank = itemView.findViewById(R.id.textRank);
            myTextAuthor = itemView.findViewById(R.id.textAuthor);
            myTextYear = itemView.findViewById(R.id.textYear);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

            contextMenu.add(0, MENU_ID_ADD, getAdapterPosition(), "添加 ");
            contextMenu.add(0, MENU_ID_UPDATE, getAdapterPosition(), "编辑 ");
            contextMenu.add(0, MENU_ID_DELETE, getAdapterPosition(), "删除 ");
        }
    }
}