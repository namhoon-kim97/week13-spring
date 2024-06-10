package week13.board.util;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import week13.board.domain.Comment;
import week13.board.domain.Post;
import week13.board.exception.CustomException;
import week13.board.exception.ErrorCode;

import java.util.Collection;

public class SecurityUtil {
    public static String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    // 현재 사용자가 관리자 권한을 가지고 있는지 확인하는 메서드
    public static boolean isAdmin() {
        Collection<? extends GrantedAuthority> authorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public static void verifyPermission(Post post) {
        String currentUsername = getCurrentUsername();
        if (!post.getUser().getUsername().equals(currentUsername) && !isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

    public static void verifyCommentPermission(Comment comment) {
        String currentUsername = getCurrentUsername();
        if (!comment.getUser().getUsername().equals(currentUsername) && !isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }
    }
}
