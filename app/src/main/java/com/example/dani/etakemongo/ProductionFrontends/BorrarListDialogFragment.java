package com.example.dani.etakemongo.ProductionFrontends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dani.etakemongo.Modelo.Usuario;
import com.example.dani.etakemongo.R;
import com.example.dani.etakemongo.SysTools.GitHubClient;
import com.example.dani.etakemongo.SysTools.RetrofitOwn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     BorrarListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 * <p>You activity (or fragment) needs to implement {@link BorrarListDialogFragment.Listener}.</p>
 */
public class BorrarListDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private Listener mListener;
    String tag = "Borrar"; // tag que indica el ciclo de vida de la app

    // TODO: Customize parameters
    public static BorrarListDialogFragment newInstance(int itemCount) {
        final BorrarListDialogFragment fragment = new BorrarListDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_ajustes_usuario, null);

        builder.setMessage("¿Seguro que quiere eliminar su cuenta?")
            .setTitle("Confirmacion")
            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    Log.i("Aviso", "Su cuenta ha sido eliminada");
                    RetrofitOwn retro = new RetrofitOwn();
                    Retrofit retrofit = retro.getObjectRetrofit();

                    // Create an instance of our GitHub API interface.
                    GitHubClient borrar = retrofit.create(GitHubClient.class);
                    Usuario usuario = new Usuario();

                    // Create a call instance for looking up Retrofit contributors.
                    Call<Usuario> call = borrar.borrar(usuario);

                    // Fetch and print a list of the contributors to the library.
                    call.enqueue(new Callback() {

                        //***************Comprobacion de que recoge los datos**********
                        @Override
                        public void onResponse(Call call, Response response) {
                            Usuario contributor = (Usuario) response.body();
                            Log.d(tag, "Los datos han borrados");
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                    dialog.cancel();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.i("Dialogos", "Confirmacion Cancelada.");
                    dialog.cancel();
                }
            });
            return builder.create();
        }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_borrar_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BorrarAdapter(getArguments().getInt(ARG_ITEM_COUNT)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            mListener = (Listener) parent;
        } else {
            mListener = (Listener) context;
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onBorrarClicked(int position);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.fragment_borrar_list_dialog_item, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);
            text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onBorrarClicked(getAdapterPosition());
                        dismiss();
                    }
                }
            });
        }

    }

    private class BorrarAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final int mItemCount;

        BorrarAdapter(int itemCount) {
            mItemCount = itemCount;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.text.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return mItemCount;
        }

    }

}
