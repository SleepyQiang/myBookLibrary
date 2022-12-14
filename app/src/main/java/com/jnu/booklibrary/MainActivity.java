package com.jnu.booklibrary;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnu.booklibrary.data.Book;
import com.jnu.booklibrary.data.DataSaver;
import com.jnu.booklibrary.dialog.MyDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView myRecyclerView;
    private Toolbar myToolbar;
    private ActionBarDrawerToggle myToggle;
    private MyAdapter myMyAdapter;
    private DrawerLayout myDrawerLayout;
    private LinearLayout myLinearLayout;
    private TextView myDrawerText;
    private FloatingActionButton myFab;
    private Spinner mySpinner;
    private ArrayList<String> mySpinner_list;
    private ArrayAdapter<String> mySpinner_list_adapter;

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
                int position = bundle.getInt("position");
                myBookList.add(position, new Book(name, R.drawable.wa, "莫言", " 浙江文艺出版社", "2017-1-1", "8.9", "9787533946661"));

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
                int position = bundle.getInt("position");
                myBookList.get(position).setTitle(name);

                new DataSaver().Save(this, myBookList);
                myMyAdapter.notifyItemChanged(position);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        myIdentityCard = findViewById(R.id.my_identity_card);
//        myIdentityCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                myDrawerLayout.openDrawer(myLinearLayout);
//            }
//        });

        myRecyclerView = this.findViewById(R.id.recyclerview);

//        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        myToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(myToolbar);
        myDrawerLayout = findViewById(R.id.drawer_main);
        myDrawerText = findViewById(R.id.drawer_text);
        myLinearLayout = findViewById(R.id.drawer_left);

        // 实现左上角菜单图标打开drawer，目前存在bug
//        myToggle = new ActionBarDrawerToggle(this, myDrawerLayout, myToolbar, R.string.welcome_to_drawer, R.string.sure_to_delete);
//        myToggle.syncState();

        myDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

//        myFab = findViewById(R.id.floatingActionButton);
//      Drawable myDrawable = getDrawable(R.drawable.ic_baseline_add_24);
//        myFab.setImageDrawable(myDrawable);

        Book b0 = new Book("家", R.drawable.jia, "巴金", "人民文学出版社", "2013-6-1", "8.3", "9787020096466");
        Book b1 = new Book("春", R.drawable.chun, "巴金", "人民文学出版社", "2013-6-1", "8.3", "9787020096473");
        Book b2 = new Book("秋", R.drawable.qiu, "巴金", "人民文学出版社", "2013-6-1", "8.5", "9787020096480");

        mySpinner = findViewById(R.id.spinner);
        mySpinner_list = new ArrayList<String>();

        mySpinner_list.add("All");
        mySpinner_list.add("Fragment1");
        mySpinner_list.add("Fragment2");
        mySpinner_list_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_white, mySpinner_list);
        mySpinner_list_adapter.setDropDownViewResource(R.layout.spinner_drop_down_white);
        mySpinner.setAdapter(mySpinner_list_adapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(MainActivity.this, "You choose " + mySpinner_list_adapter.getItem(i), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        break;
                    case 2:
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

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
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