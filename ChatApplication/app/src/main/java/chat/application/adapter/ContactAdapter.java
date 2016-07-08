package chat.application.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import chat.application.R;
import chat.application.activity.AddContactActivity;
import chat.application.model.Contact;


@SuppressWarnings("ALL")
public class ContactAdapter extends ArrayAdapter<Contact>
{
    ArrayList<Contact> list;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    Context context;

    public ContactAdapter(Context context, int resource, ArrayList<Contact> objects)
    {
        super(context, resource, objects);
        this.context = context;
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        list = objects;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Contact getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent)
    {
        // convert view = design
        View view = convertView;
        if (view == null)
        {
            holder = new ViewHolder();
            view = vi.inflate(Resource, null);

            holder.tvName = (TextView) view.findViewById(R.id.tv_itemContact_name);
            holder.imSetting = (ImageView) view.findViewById(R.id.image_setting_contact);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvName.setText(list.get(position).getUsername()+":"+list.get(position).getPublic_key());


        holder.imSetting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddContactActivity.ISNEWCONTACT =false;
                Intent i = new Intent(context, AddContactActivity.class);
                i.putExtra("id",list.get(position).getId());
                i.putExtra("name", list.get(position).getUsername());
                i.putExtra("image", list.get(position).getImage());
                i.putExtra("key", list.get(position).getPublic_key());

                context.startActivity(i);
            }
        });

        return view;
    }
    static class ViewHolder
    {
        protected TextView tvName;
        protected ImageView imSetting;
    }
}