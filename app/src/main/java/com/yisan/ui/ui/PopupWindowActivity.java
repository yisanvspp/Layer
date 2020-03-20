package com.yisan.ui.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yisan.ui.R;
import com.yisan.ui.utils.ScreenUtils;
import com.yisan.ui.utils.ToastUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class PopupWindowActivity extends AppCompatActivity {


    private Button btn1, btn2, btn3, btn4,btn5;

    public static void show(Context context) {
        context.startActivity(new Intent(context, PopupWindowActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_window);


        btn1 = findViewById(R.id.pop_1);
        btn2 = findViewById(R.id.pop_2);
        btn3 = findViewById(R.id.pop_3);
        btn4 = findViewById(R.id.pop_4);
        btn5 = findViewById(R.id.pop_5);

        /**
         * 原生PopupWindow
         */
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop1();
            }
        });

        /**
         * 原生PopupWindow。+ RecyclerView 列表
         */
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> list = new ArrayList<>();
                list.add("1");
                list.add("2");
                list.add("3");
                pop2(list);
            }
        });

        /**
         * PopMenu使用方法
         */
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop3();
            }
        });


        /**
         * 底部弹出菜单
         */
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pop4();
            }
        });

        /**
         * ListPopupWindow
         */
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop5();
            }
        });


    }

    /********************PopupWindow**************************/

    private void pop1() {

        View popView = LayoutInflater.from(PopupWindowActivity.this).inflate(R.layout.pop_view, null);
        TextView tvTitle = popView.findViewById(R.id.tv_title);
        Button btnOk = popView.findViewById(R.id.btn_ok);
        Button btnNo = popView.findViewById(R.id.btn_no);
        tvTitle.setText("xx");

        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D81B60")));
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.showAsDropDown(btn1, 0, 0, Gravity.START);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("no");
                popupWindow.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("yes");
                popupWindow.dismiss();
            }
        });
    }


    /********************PopupWindow + RecyclerView**************************/
    private void pop2(List<String> data) {

        View popView = LayoutInflater.from(PopupWindowActivity.this).inflate(R.layout.pop2_view, null);
        RecyclerView recyclerView = popView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(PopupWindowActivity.this));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(PopupWindowActivity.this,
                DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_item_white, null));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MenuAdapter adapter = new MenuAdapter(data);
        recyclerView.setAdapter(adapter);

        final PopupWindow popupWindow = new PopupWindow(popView, ScreenUtils.dip2px(PopupWindowActivity.this, 100),
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.pop_animation);
        popupWindow.showAsDropDown(btn2);
        adapter.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View view, int position) {
                ToastUtils.show(String.valueOf(position + 1));
                popupWindow.dismiss();
            }
        });
    }

    class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
        private OnMenuItemClickListener mListener;
        private List<String> list;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvText;
            LinearLayout root;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvText = itemView.findViewById(R.id.tv_str);
                root = itemView.findViewById(R.id.ll_root);
            }
        }

        public MenuAdapter(List<String> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.rcycler_view_item_pop, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            try {
                String str = list.get(position);
                holder.tvText.setText(str);
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onMenuItemClick(v, position);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
            this.mListener = listener;
        }
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(View view, int position);
    }


    /********************PopupMenu**************************/

    private void pop3() {

        PopupMenu popupMenu = new PopupMenu(PopupWindowActivity.this, btn3);
        //菜单填充器
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        //填充菜单
        menuInflater.inflate(R.menu.pop_menu, popupMenu.getMenu());
        //靠右上角对齐显示
        popupMenu.setGravity(Gravity.END);
        try {
            //通过反射显示图标
            Field field = popupMenu.getClass().getDeclaredField("mPopup");
            field.setAccessible(true);
            MenuPopupHelper helper = (MenuPopupHelper) field.get(popupMenu);
            helper.setForceShowIcon(true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.exit:
                        ToastUtils.show("exit");
                        break;
                    case R.id.set:
                        ToastUtils.show("set");
                        break;
                    case R.id.account:
                        ToastUtils.show("account");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }


    /********************底部弹出 + PopupWindow**************************/

    private void pop4() {
        View popView = LayoutInflater.from(PopupWindowActivity.this).inflate(R.layout.pop4_view, null);
        Button b1 = popView.findViewById(R.id.btn_album);
        Button b2 = popView.findViewById(R.id.btn_take);
        Button b3 = popView.findViewById(R.id.btn_cancel);

        final PopupWindow popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.phone_pop_animation);
        popupWindow.showAtLocation(btn4, Gravity.BOTTOM, 0, 0);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("相册");
                popupWindow.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("拍摄");
                popupWindow.dismiss();
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("取消");
                popupWindow.dismiss();
            }
        });

    }

    /********************ListPopupWindow**************************/
    private void pop5() {

        final List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PopupWindowActivity.this,
                android.R.layout.simple_list_item_1,
                list);
        final ListPopupWindow listPopupWindow = new ListPopupWindow(PopupWindowActivity.this);
        listPopupWindow.setAdapter(adapter);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setAnchorView(btn5);
        listPopupWindow.setModal(true);
//        listPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("ffffff")));
        listPopupWindow.setDropDownGravity(Gravity.END);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(list.get(position));
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();

    }

}
