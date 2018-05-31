package com.braisgabin.ghostviewerror.ghostviewerror;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(getLayoutInflater(), this);
        recyclerView.setAdapter(adapter);


        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, OnSharedElementsReadyListener listener) {
                super.onSharedElementsArrived(sharedElementNames, sharedElements, listener);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(this, ChildActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, v, "button").toBundle());
    }

    static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private final LayoutInflater layoutInflater;
        private final View.OnClickListener listener;

        public Adapter(LayoutInflater layoutInflater, View.OnClickListener listener) {
            this.layoutInflater = layoutInflater;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return ViewHolder.create(layoutInflater, parent, listener);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // no-op
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            static ViewHolder create(LayoutInflater inflater, ViewGroup parent, View.OnClickListener listener) {
                final View view = inflater.inflate(R.layout.item, parent, false);

                return new ViewHolder(view, listener);
            }

            public ViewHolder(View itemView, View.OnClickListener listener) {
                super(itemView);
                itemView.setOnClickListener(listener);
            }
        }
    }
}
