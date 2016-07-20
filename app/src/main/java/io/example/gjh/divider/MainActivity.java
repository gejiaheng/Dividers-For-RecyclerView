package io.example.gjh.divider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import io.example.gjh.divider.decorator.InsetDivider;
import io.example.gjh.divider.decorator.OverlayDivider;
import io.example.gjh.divider.decorator.UnderneathDivider;

/**
 * Just a simple demo to demonstrate three divider's implementations.
 *
 * @author gejiaheng
 * @since @since 07-19-2016
 */
public class MainActivity extends AppCompatActivity implements DividerPicker.OnDividerSelectedListener {

    /**
     * @see UnderneathDivider
     */
    public static final int DIVIDER_UNDERNEATH = 0x00;
    /**
     * @see OverlayDivider
     */
    public static final int DIVIDER_OVERLAY = 0x01;
    /**
     * @see InsetDivider
     */
    public static final int DIVIDER_INSET = 0x02;

    protected RecyclerView mRecyclerView;
    @DividerType
    private int mCurrentDividerType = DIVIDER_UNDERNEATH;
    private SparseArray<RecyclerView.ItemDecoration> mDividers = new SparseArray<>();

    @IntDef({DIVIDER_UNDERNEATH, DIVIDER_OVERLAY, DIVIDER_INSET})
    public @interface DividerType {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(getDivider(mCurrentDividerType)); // default

        mRecyclerView.setAdapter(new SimpleAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DividerPicker.newInstance(mCurrentDividerType).show(getSupportFragmentManager(), DividerPicker.class.getSimpleName());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDividerSelected(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
        switchDivider(i);
    }

    private RecyclerView.ItemDecoration getDivider(@DividerType int type) {
        RecyclerView.ItemDecoration divider = mDividers.get(type);
        if (divider == null) {
            switch (type) {
                case DIVIDER_UNDERNEATH:
                    divider = new UnderneathDivider(this, UnderneathDivider.VERTICAL_LIST);
                    break;
                case DIVIDER_OVERLAY:
                    divider = new OverlayDivider(this, OverlayDivider.VERTICAL_LIST);
                    break;
                case DIVIDER_INSET:
                    divider = new InsetDivider.Builder(this)
                            .orientation(InsetDivider.VERTICAL_LIST)
                            .dividerHeight(getResources().getDimensionPixelSize(R.dimen.divider_height))
                            .color(getResources().getColor(R.color.colorAccent))
                            .insets(getResources().getDimensionPixelSize(R.dimen.divider_inset), 0)
                            .build();
                    break;
            }
            mDividers.put(type, divider);
        }
        return divider;
    }

    private void switchDivider(int type) {
        if (mCurrentDividerType == type)
            return;

        RecyclerView.ItemDecoration current = getDivider(mCurrentDividerType);
        mRecyclerView.removeItemDecoration(current);
        mRecyclerView.addItemDecoration(getDivider(type));
        mRecyclerView.invalidate(); // need to invalidate RecyclerView to make the change work.
        mCurrentDividerType = type;
    }

    static class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {

        @Override
        public SimpleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView itemView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tile, parent, false);
            return new SimpleAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(SimpleAdapter.ViewHolder holder, int position) {
            holder.mText.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            TextView mText;

            public ViewHolder(TextView itemView) {
                super(itemView);
                mText = itemView;
            }
        }
    }
}
