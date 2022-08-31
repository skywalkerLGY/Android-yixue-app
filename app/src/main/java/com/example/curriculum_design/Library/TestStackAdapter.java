package com.example.curriculum_design.Library;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.bifan.txtreaderlib.main.TxtConfig;
import com.bifan.txtreaderlib.ui.HwTxtPlayActivity;
import com.example.curriculum_design.R;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

public class TestStackAdapter extends StackAdapter<Integer> {
    Context context;
    public TestStackAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        view = getLayoutInflater().inflate(R.layout.list_book_item, parent, false);
        return new ColorItemViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.list_book_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mTextTitle;
        Button btn_to_read;
        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            btn_to_read= view.findViewById(R.id.goto_read);
        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(Integer data, final int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_OVER);
            mTextTitle.setText(Book_Path_Name.book_names[position]);
            btn_to_read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TxtConfig.saveIsOnVerticalPageMode(getContext(),false);
                HwTxtPlayActivity.loadTxtFile(getContext(), Book_Path_Name.path[position]);
                }
            });
        }

    }
}
