// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MyBaseActivity$$ViewBinder<T extends com.usr.usrsimplebleassistent.MyBaseActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230912, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230912, "field 'toolbar'");
  }

  @Override public void unbind(T target) {
    target.toolbar = null;
  }
}
