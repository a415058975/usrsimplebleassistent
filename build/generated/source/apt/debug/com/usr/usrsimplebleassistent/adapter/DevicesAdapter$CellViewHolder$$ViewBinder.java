// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class DevicesAdapter$CellViewHolder$$ViewBinder<T extends com.usr.usrsimplebleassistent.adapter.DevicesAdapter.CellViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230885, "field 'tvDevName'");
    target.tvDevName = finder.castView(view, 2131230885, "field 'tvDevName'");
    view = finder.findRequiredView(source, 2131230886, "field 'tvDevSignal'");
    target.tvDevSignal = finder.castView(view, 2131230886, "field 'tvDevSignal'");
    view = finder.findRequiredView(source, 2131230884, "field 'tvDevMac'");
    target.tvDevMac = finder.castView(view, 2131230884, "field 'tvDevMac'");
  }

  @Override public void unbind(T target) {
    target.tvDevName = null;
    target.tvDevSignal = null;
    target.tvDevMac = null;
  }
}
