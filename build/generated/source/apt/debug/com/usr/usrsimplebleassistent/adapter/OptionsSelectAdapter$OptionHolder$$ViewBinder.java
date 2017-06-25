// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OptionsSelectAdapter$OptionHolder$$ViewBinder<T extends com.usr.usrsimplebleassistent.adapter.OptionsSelectAdapter.OptionHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230743, "field 'btnOption'");
    target.btnOption = finder.castView(view, 2131230743, "field 'btnOption'");
  }

  @Override public void unbind(T target) {
    target.btnOption = null;
  }
}
