package com.jnu.mybookshop;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.jnu.mybookshop.data.Book;
import com.jnu.mybookshop.data.DataSaver;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CardView myIdentityCard;

    RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;

    /*private Spinner spinner;
    private ArrayList<String> spinner_list;
    private ArrayAdapter<String> spinner_list_adapter;*/

    private TabLayout myTabLayout;

    private DrawerLayout myDrawerLayout;
    private LinearLayout myLinearLayout;
    private TextView myDrawerText;

    private FloatingActionButton myFab;

    ArrayList<Book> mBookList = new ArrayList<>();
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
                mBookList.add(position, new Book(name, R.drawable.book_no_name));
                new DataSaver().Save(this, mBookList);
                mMyAdapter.notifyItemInserted(position);
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
                mBookList.get(position).setTitle(name);
                new DataSaver().Save(this, mBookList);
                mMyAdapter.notifyItemChanged(position);
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myIdentityCard = findViewById(R.id.my_identity_card);
        myIdentityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDrawerLayout.openDrawer(myLinearLayout);
            }
        });

        mRecyclerView = this.findViewById(R.id.recyclerview);

        /*MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24);*/

        myDrawerLayout = findViewById(R.id.drawer_main);
        myDrawerText = findViewById(R.id.drawer_text);
        myLinearLayout = findViewById(R.id.drawer_left);
        myDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                Toast.makeText(MainActivity.this, "Drawer open", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                Toast.makeText(MainActivity.this, "Drawer closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        myTabLayout = findViewById(R.id.tabLayout);
        myTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        Toast.makeText(MainActivity.this,"Monday selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(MainActivity.this,"Tuesday selected",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this,"Wednesday selected",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        myFab = findViewById(R.id.floatingActionButton);
        Drawable myDrawable = getDrawable(R.drawable.ic_baseline_add_24);
        myFab.setImageDrawable(myDrawable);

        Book b0 = new Book("三体", R.drawable.threebody1);
        Book b1 = new Book("三体Ⅱ", R.drawable.threebody2);
        Book b2 = new Book("三体Ⅲ", R.drawable.threebody3);

        /*spinner = findViewById(R.id.spinner);
        spinner_list = new ArrayList<String>();
        spinner_list.add("All");
        spinner_list.add("Fragment1");
        spinner_list.add("Fragment2");
        spinner_list_adapter = new ArrayAdapter<String>(this, R.layout.spinner_item_white, spinner_list);
        spinner_list_adapter.setDropDownViewResource(R.layout.spinner_drop_down_white);
        spinner.setAdapter(spinner_list_adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(MainActivity.this, "You choose " + spinner_list_adapter.getItem(i), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        break;
                    case 2:
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        DataSaver dataSaver = new DataSaver();
        mBookList = dataSaver.Load(this);

        if (mBookList.size() == 0) {
            mBookList.add(b0);
            mBookList.add(b1);
            mBookList.add(b2);
        }

        mMyAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);//RecycleView implementation

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputBookListActivity.class);
                intent.putExtra("position", 0);
                addDataLauncher.launch(intent);
            }
        }); //FloatingActionButton implementation
    }

    public List<Book> getListBooks() {
        return mBookList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;
            case R.id.app_bar_search:
                Toast.makeText(MainActivity.this, "You choose " +item.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_collection:
                Toast.makeText(MainActivity.this, "You choose " +item.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_fontsize:
                Toast.makeText(MainActivity.this, "You choose " +item.toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_share:
                Toast.makeText(MainActivity.this, "You choose " +item.toString(), Toast.LENGTH_SHORT).show();
                return true;

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
                intentUpdate.putExtra("name", mBookList.get(item.getOrder()).getTitle());
                updateDataLauncher.launch(intentUpdate);
                break;
            case 3:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mBookList.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this, mBookList);
                                mMyAdapter.notifyItemRemoved(item.getOrder());
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
            Book book = mBookList.get(position);
            holder.mImageView.setImageDrawable(getResources().getDrawable(book.coverresourceid));
            holder.mTextView.setText(book.title);
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView mImageView;
        TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, MENU_ID_ADD, getAdapterPosition(), "Add " + getAdapterPosition());
            contextMenu.add(0, MENU_ID_UPDATE, getAdapterPosition(), "Update " + getAdapterPosition());
            contextMenu.add(0, MENU_ID_DELETE, getAdapterPosition(), "Delete " + getAdapterPosition());
        }
    }

}