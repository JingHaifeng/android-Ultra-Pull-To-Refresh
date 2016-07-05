package in.srain.cube.views.ptr;

import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * A single linked list to wrap PtrUIHandler
 */
class PtrUIHandlerHolder implements PtrUIHandler {

    private PtrUIHandler mHeaderHandler;
    private PtrUIHandler mFooterHandler;
    private PtrUIHandlerHolder mNext;

    private boolean containsHeader(PtrUIHandler handler) {
        return mHeaderHandler != null && mHeaderHandler == handler;
    }

    private boolean containsFooter(PtrUIHandler handler) {
        return mFooterHandler != null && mFooterHandler == handler;
    }

    private PtrUIHandlerHolder() {

    }

    public boolean hasHeaderHandler() {
        return mHeaderHandler != null;
    }

    public boolean hasFooterHandler() {
        return mFooterHandler != null;
    }

    private PtrUIHandler getHeaderHandler() {
        return mHeaderHandler;
    }

    public PtrUIHandler getFooterHandler() {
        return mFooterHandler;
    }

    public static void addHeaderHandler(PtrUIHandlerHolder holder, PtrUIHandler handler) {

        if (null == handler) {
            return;
        }
        if (holder == null) {
            return;
        }
        if (null == holder.mHeaderHandler) {
            holder.mHeaderHandler = handler;
            return;
        }

        PtrUIHandlerHolder current = holder;
        for (; ; current = current.mNext) {

            // duplicated
            if (current.containsHeader(handler)) {
                return;
            }
            if (current.mNext == null) {
                break;
            }
        }

        PtrUIHandlerHolder newHolder = new PtrUIHandlerHolder();
        newHolder.mHeaderHandler = handler;
        current.mNext = newHolder;
    }


    public static void addFooterHandler(PtrUIHandlerHolder holder, PtrUIHandler handler) {

        if (null == handler) {
            return;
        }
        if (holder == null) {
            return;
        }
        if (null == holder.mFooterHandler) {
            holder.mHeaderHandler = handler;
            return;
        }

        PtrUIHandlerHolder current = holder;
        for (; ; current = current.mNext) {

            // duplicated
            if (current.containsHeader(handler)) {
                return;
            }
            if (current.mNext == null) {
                break;
            }
        }

        PtrUIHandlerHolder newHolder = new PtrUIHandlerHolder();
        newHolder.mHeaderHandler = handler;
        current.mNext = newHolder;
    }



    public static PtrUIHandlerHolder create() {
        return new PtrUIHandlerHolder();
    }

    public static PtrUIHandlerHolder removeHandler(PtrUIHandlerHolder head, PtrUIHandler handler) {
        if (head == null || handler == null || null == head.mHeaderHandler) {
            return head;
        }

        PtrUIHandlerHolder current = head;
        PtrUIHandlerHolder pre = null;
        do {

            // delete current: link pre to next, unlink next from current;
            // pre will no change, current move to next element;
            if (current.containsHeader(handler)) {

                // current is head
                if (pre == null) {

                    head = current.mNext;
                    current.mNext = null;

                    current = head;
                } else {

                    pre.mNext = current.mNext;
                    current.mNext = null;
                    current = pre.mNext;
                }
            } else {
                pre = current;
                current = current.mNext;
            }

        } while (current != null);

        if (head == null) {
            head = new PtrUIHandlerHolder();
        }
        return head;
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler handler = current.getHeaderHandler();
            if (null != handler) {
                handler.onUIReset(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (!hasHeaderHandler()) {
            return;
        }
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler handler = current.getHeaderHandler();
            if (null != handler) {
                handler.onUIRefreshPrepare(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler handler = current.getHeaderHandler();
            if (null != handler) {
                handler.onUIRefreshBegin(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler handler = current.getHeaderHandler();
            if (null != handler) {
                handler.onUIRefreshComplete(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        PtrUIHandlerHolder current = this;
        do {
            final PtrUIHandler handler = current.getHeaderHandler();
            if (null != handler) {
                handler.onUIPositionChange(frame, isUnderTouch, status, ptrIndicator);
            }
        } while ((current = current.mNext) != null);
    }
}
