package pl.almestinio.socialapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.almestinio.socialapp.R;
import pl.almestinio.socialapp.http.post.Post_;
import pl.almestinio.socialapp.ui.profileView.ProfileViewContracts;

/**
 * Created by mesti193 on 3/9/2018.
 */

public class ProfileGalleryAdapter extends RecyclerView.Adapter<ProfileGalleryAdapter.ViewHolder> {

    private ProfileViewContracts.ProfileViewPresenter profileViewPresenter;
    private List<Post_> postsList;
    private Context context;
    private View view;


    public ProfileGalleryAdapter(List<Post_> postsList, Context context, ProfileViewContracts.ProfileViewPresenter profileViewPresenter){
        this.postsList = postsList;
        notifyItemRangeChanged(0, postsList.size());
        this.context = context;
        this.profileViewPresenter = profileViewPresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post_ post = postsList.get(position);

        try{
            Picasso.with(context).load(post.getPostPic()).fit().centerCrop().into(holder.imageViewProfileGallery);
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.imageViewProfileGallery.setOnClickListener(v -> profileViewPresenter.onUserPhotoClick(post.getPostPic()));

    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewProfileGallery)
        ImageView imageViewProfileGallery;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}