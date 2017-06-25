// Generated code from Butter Knife. Do not modify!
package com.usr.usrsimplebleassistent.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessagesAdapter$MessageViewHolderRight$$ViewBinder<T extends com.usr.usrsimplebleassistent.adapter.MessagesAdapter.MessageViewHolderRight> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230887, "field 'tvMsgContent'");
    target.tvMsgContent = finder.castView(view, 2131230887, "field 'tvMsgContent'");
  }

  @Override public void unbind(T target) {
    target.tvMsgContent = null;
  }
}
